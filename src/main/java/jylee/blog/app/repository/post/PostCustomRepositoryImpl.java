package jylee.blog.app.repository.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jylee.blog.app.entity.*;
import jylee.blog.app.repository.mybatis.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static jylee.blog.app.entity.QPost.*;
import static jylee.blog.app.entity.QPostTag.*;
import static jylee.blog.app.entity.QTag.tag;

@Repository
//@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final Mapper mapper;
    private final JPAQueryFactory query;

    public PostCustomRepositoryImpl(EntityManager em, Mapper mapper) {
        this.mapper = mapper;
        query = new JPAQueryFactory(em);
    }

    /**
     * select p1_0.post_id,
     * p1_0.content,
     * p1_0.created_by,
     * p1_0.created_date,
     * p1_0.last_modified_by,
     * p1_0.last_modified_date,
     * p2_0.post_id,
     * p2_0.post_tag_id,
     * t1_0.tag_id,
     * t1_0.content,
     * p1_0.title
     * from post p1_0
     * join post_tag p2_0 on p1_0.post_id = p2_0.post_id
     * join tag t1_0 on t1_0.tag_id = p2_0.tag_id
     * where p1_0.post_id = 12;
     */
    @Override
    public Post findPostFetchById(Long id) {
        return query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .where(post.id.eq(id)).fetchOne();
    }

    /**
     * select p1_0.post_id,
     * p1_0.content,
     * p1_0.created_by,
     * p1_0.created_date,
     * p1_0.last_modified_by,
     * p1_0.last_modified_date,
     * p2_0.post_id,
     * p2_0.post_tag_id,
     * t1_0.tag_id,
     * t1_0.content,
     * p1_0.title
     * from post p1_0
     * join post_tag p2_0 on p1_0.post_id = p2_0.post_id
     * join tag t1_0 on t1_0.tag_id = p2_0.tag_id
     * order by p1_0.post_id desc;
     */
    @Override
    public List<Post> findPostAll(Long offset, int size, String sort) {
        return query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .offset(offset).limit(size).orderBy(getSortValue(sort))
                .fetch();
    }

    /**
     * select p1_0.post_id,
     * p1_0.content,
     * p1_0.created_by,
     * p1_0.created_date,
     * p1_0.last_modified_by,
     * p1_0.last_modified_date,
     * p2_0.post_id,
     * p2_0.post_tag_id,
     * t1_0.tag_id,
     * t1_0.content,
     * p1_0.title
     * from post p1_0
     * join post_tag p2_0 on p1_0.post_id = p2_0.post_id
     * join tag t1_0 on t1_0.tag_id = p2_0.tag_id
     * where p2_0.post_id in (select p4_0.post_id from post_tag p4_0 where p4_0.tag_id = 7)
     * order by p1_0.post_id desc;
     */
    @Override
    public List<Post> findPostAllByTagId(Long offset, int size, String sort, Long id) {
        return query
                .selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .where(postTag.post.in(
                        select(postTag.post)
                                .from(postTag)
                                .where(postTag.tag.id.eq(id))
                ))
                .offset(offset).limit(size).orderBy(getSortValue(sort))
                .fetch();
    }

    /**
     * SELECT p.*
     * FROM post p
     * JOIN post_tag pt ON p.post_id = pt.post_id
     * JOIN tag t ON pt.tag_id = t.tag_id
     * WHERE p.title LIKE ?1 OR t.content = ?1
     * ORDER BY p.id ASC OFFSET ?2 ROWS FETCH NEXT ?3 ROWS ONLY;
     */
    @Override
    public List<Post> findPostByKeyword(Long offset, int size, String sort, String keyword) {
        return query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .where(
                        post.title.like(keyword)
                                .or(tag.content.eq(keyword)))
                .offset(offset).limit(size).orderBy(getSortValue(sort))

                .fetch();
    }

    private static OrderSpecifier<Long> getSortValue(String sort) {
        return sort.equals("asc") ? post.id.asc() : post.id.desc();
    }


    @Override
    public Long findPrevPostId(Long id) {
        return query.select(post.id).from(post).where(post.id.lt(id)).orderBy(post.id.desc()).limit(1).fetchFirst();
    }

    @Override
    public Long findNextPostId(Long id) {
        return query.select(post.id).from(post).where(post.id.gt(id)).limit(1).fetchFirst();
    }

}
