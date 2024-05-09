package jylee.blog.app.repository.post;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jylee.blog.app.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static jylee.blog.app.entity.QPost.*;
import static jylee.blog.app.entity.QPostTag.*;
import static jylee.blog.app.entity.QTag.tag;

@Repository
//@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory query;

    public PostCustomRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Post findPostFetchById(Long id) {
        return query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .where(post.id.eq(id)).fetchOne();
    }

    @Override
    public List<Post> findPostAll(Long offset, int size, String sort) {
        OrderSpecifier<Long> sortValue;
        if(sort.equals("asc")) {
            sortValue = post.id.asc();
        } else {
            sortValue = post.id.desc();
        }

        return query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .offset(offset).limit(size).orderBy(sortValue)
                .fetch();
    }


    public List<Post> findPostAllPaging(int page, int size, String sort) {
        OrderSpecifier<Long> sortValue;
        if(sort.equals("asc")) {
            sortValue = post.id.asc();
        } else {
            sortValue = post.id.desc();
        }

        List<Post> fetch = query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .offset(page).limit(size).orderBy(sortValue)
                .fetch();
        return fetch;
    }

    @Override
    public List<Post> findPostAllByTagId(Long id) {
        return query
                .selectFrom(post)
                .join(post.postTags, postTag).fetchJoin()
                .join(postTag.tag, tag).fetchJoin()
                .where(postTag.post.in(
                        select(postTag.post)
                                .from(postTag)
                                .where(postTag.tag.id.eq(id))
                ))
                .orderBy(post.id.desc())
                .fetch();
    }


}
