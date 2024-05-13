package jylee.blog.app.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jylee.blog.app.entity.*;
import jylee.blog.app.repository.mybatis.Mapper;
import jylee.blog.app.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static jylee.blog.app.entity.QPost.*;
import static jylee.blog.app.entity.QPostTag.postTag;
import static jylee.blog.app.entity.QTag.tag;

@SpringBootTest
@Transactional
class PostRepositoryTest {
    @Autowired
    PostRepository repository;

    @Autowired
    Mapper countMapper;

    @Autowired
    EntityManager em;
    JPAQueryFactory query;
    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void beforeEach() {
        query = new JPAQueryFactory(em);
    }
    @Test
    public void test() {
        List<Post> fetch = query.selectFrom(post)
                .leftJoin(post.postTags, postTag).fetchJoin()
                .leftJoin(postTag.tag, tag).fetchJoin()
                .where(post.title.like("19번").or(tag.content.eq("태그3")))
                .fetch();



        System.out.println("fetch = " + fetch);
    }

    @Test
    void test2() {
        String keyword = "제목4";
//        Long l = countMapper.countByKeyword(keyword);
//        System.out.println("l = " + l);

        List<Long> fetch = query.select(post.id).distinct()
                .from(post)
                .join(post.postTags, postTag)
                .join(postTag.tag, tag)
                .where(post.title.contains(keyword).or(tag.content.eq(keyword))).fetch();
        int size = fetch.size();


        System.out.println("size = " + size);

        Long l = countMapper.countByKeyword(keyword);
        System.out.println("l = " + l);
    }

    @Autowired
    Mapper mapper;

    @Test
    void test3 () {
//
//        PageRequest pageRequest = PageRequest.of(1, 10);
//        List<PostResponse> collect = postRepository.findAll(pageRequest).stream().map(PostResponse::new).collect(Collectors.toList());
//
//        PageImpl<PostResponse> postResponses = new PageImpl<>(collect, pageRequest, collect.size());
//        CustomPage pageInfo = new CustomPage(postResponses);
//        System.out.println("postResponses = " + postResponses);
//
//        Post findFirstByOrderByIdDesc = postRepository.findFirstByOrderByIdDesc();
//
        Long id = 3L;
//        QPost prevQ = new QPost("prev");
//        QPost nextQ = new QPost("next");
//
//        JPQLQuery<Long> prev = select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).limit(1);
//        JPQLQuery<Long> next = select(nextQ.id).from(nextQ).where(nextQ.id.gt(id)).limit(1);
//        query.select(post, prev, next).from(post, prevQ, nextQ).fetch();
////        select *, (select POST_ID from post where POST_ID < 5 order by POST.POST_ID desc limit 1) as prev_id
////        from post
////        where post_id = 5;
//
//        JPQLQuery<Long> lt = select(post.id).from(post).where(post.id.lt(id));
//        JPQLQuery<Long> gt = select(post.id).from(post).where(post.id.gt(id));
//
//        query.select(post, lt, gt)
//                .leftJoin(post.postTags, postTag).fetchJoin()
//                .leftJoin(postTag.tag, tag).fetchJoin()
//                .where(post.id.eq(id)).fetchOne();

//        QPost prevQ = new QPost("prev");
//        QPost nextQ = new QPost("next");
//        JPQLQuery<Long> next = select(nextQ.id).from(nextQ).where(nextQ.id.gt(id)).limit(1);
//        List<Post> fetch = query.selectFrom(prevQ).fetch();
//        id = 1L;
//        List<Long> fetch1 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).fetch();
//        BooleanExpression exists1 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).exists();
//
//        id = 2L;
//        List<Long> fetch2 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).fetch();
//        BooleanExpression exists2 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).exists();
//
//        id = 3L;
//        List<Long> fetch3 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).fetch();
//        BooleanExpression exists3 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).exists();
//
//
//        id = 8L;
//        List<Long> fetch8 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).fetch();
//        BooleanExpression exists8 = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).exists();
//

//        getaLong(1L);
//        getaLong(2L);
//        getaLong(3L);
//        getaLong(8L);
//        getaLong(9L);

//        BooleanExpression nextExist = query.select(nextQ.id).from(nextQ).where(nextQ.id.lt(id)).exists();

//        System.out.println("f2 = " + f2);
        JPAQuery<Post> from = query.select(post).from(post);
        JPAQuery<PostTag> select = from.select(postTag);
        JPAQuery<PostTag> from1 = select.from(postTag);
        List<PostTag> fetch = from1.fetch();
//        from.fetch();

        System.out.println("fetch = " + fetch);
    }

    private void getaLong(Long id) {
        for(int i = 0 ; i < 100; i++) {
            System.out.print(id);
        }
        System.out.println();

        QPost prevQ = new QPost("prev");
        QPost nextQ = new QPost("next");
        JPAQuery<Long> prev = query.select(prevQ.id).from(prevQ).where(prevQ.id.lt(id)).orderBy(prevQ.id.desc()).limit(1);
        Long prevId = prev.fetchFirst();
        JPAQuery<Long> next = query.select(nextQ.id).from(nextQ).where(nextQ.id.gt(id)).limit(1);
        Long nextId = next.fetchFirst();



        JPAQuery<Post> select = query.select(post).from(post);



//        return f2;
//        query.select(post).select()
//        query.select()
//
//        query.select(prevQpost, preq).from(post,postTag)
//                .leftJoin(post.postTags, postTag).fetchJoin()
//                .leftJoin(postTag.tag, tag).fetchJoin()
//                .where(post.id.eq(id)).fetchOne();


    }
}