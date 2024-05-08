package jylee.blog.app.repository.post_tag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jylee.blog.app.entity.PostTag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static jylee.blog.app.entity.QPostTag.postTag;

@Repository
public class PostTagCustomRepositoryImpl implements PostTagCustomRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;


    public PostTagCustomRepositoryImpl(EntityManager em) {
        this.em = em;
        query = new JPAQueryFactory(em);
    }

    @Override
    public List<PostTag> findByPostId(Long id) {
        return query
                .selectFrom(postTag)
                .where(postTag.post.id.eq(id))
                .fetch();
    }


    @Override
    public Long deleteByPostId(Long id) {
        long execute = query.delete(postTag)
                .where(postTag.post.id.eq(id)).execute();
        em.clear();

        return execute;
    }
}
