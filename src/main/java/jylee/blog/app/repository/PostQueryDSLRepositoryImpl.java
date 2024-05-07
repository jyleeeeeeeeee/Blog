package jylee.blog.app.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jylee.blog.app.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static jylee.blog.app.entity.QPost.*;
import static jylee.blog.app.entity.QPostTag.*;
import static jylee.blog.app.entity.QTag.tag;

@Repository
//@RequiredArgsConstructor
public class PostQueryDSLRepositoryImpl implements PostQueryDSLRepository {

    private final JPAQueryFactory query;

    public PostQueryDSLRepositoryImpl(EntityManager em) {
        query = new JPAQueryFactory(em);
    }

    @Override
    public Post findPostById(Long id) {
        return query.selectFrom(post)
                .join(post.postTags, postTag).fetchJoin()
                .join(postTag.tag, tag).fetchJoin()
                .where(post.id.eq(id)).fetchOne();
    }

    @Override
    public List<Post> findPostAll() {
        return query.selectFrom(post)
                .join(post.postTags, postTag).fetchJoin()
                .join(postTag.tag, tag).fetchJoin().fetch();
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
                .fetch();
    }


}
