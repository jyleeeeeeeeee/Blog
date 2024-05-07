package jylee.blog.app.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static jylee.blog.app.entity.QPost.post;
import static jylee.blog.app.entity.QPostTag.postTag;
import static jylee.blog.app.entity.QTag.*;

@SpringBootTest
@Transactional
class PostQueryDSLRepositoryImplTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory query;

    @PersistenceUnit
    EntityManagerFactory emf;
    @Autowired
    PostRepository postRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    PostTagRepository postTagRepository;

    @BeforeEach
    void createQuery() {
        query = new JPAQueryFactory(em);
    }

    //    @BeforeEach
    void beforeEach() {
        for (int i = 0; i < 2; i++) {
            Post post = new Post(new PostRequest(i + 1 + "번 게시글 제목", "<p>저장 테스트입니다.</p><p><br></p><p>ㅋㅋ</p><p><br></p><p>ㄴㅇㄹㅁㄴㅇㄹ</p><p>ㄴㅁㅇㄹㄴㅁㅇㄹ</p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p><p><br></p><p><img src=\"/tui-editor/image-print?filename=64eec421-dbf6-487a-a666-474da9ccba4a.jpeg\" alt=\"DSC_1663.jpeg\" contenteditable=\"false\"><br></p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p>",
                    new ArrayList<>()));
            em.persist(post);

            Tag tag = new Tag("태그" + i + "" + i);
            Tag tag2 = new Tag("태그" + i + "" + (i + 1));
            em.persist(tag);
            em.persist(tag2);

            PostTag postTag = new PostTag(tag, post);
            post.addPostTagsToPost(postTag);

            PostTag postTag2 = new PostTag(tag2, post);
            post.addPostTagsToPost(postTag2);

            em.persist(postTag);
            em.persist(postTag2);
            em.flush();
            em.clear();
        }

        System.out.println("========================================");
    }

    //    @Test
    void test() {
        Post fetchOne = query.selectFrom(post).fetchOne();
        System.out.println("fetchOne = " + fetchOne);
        List<PostTag> postTags = fetchOne.getPostTags();
        System.out.println("postTags = " + postTags);
        for (PostTag postTag1 : postTags) {
            Tag tag1 = postTag1.getTag();
            System.out.println("tag1 = " + tag1);
        }
    }

    //    @Test
    void test2() {
        Post post1 = query.select(post).from(post).where(post.id.eq(1L)).fetchOne();
        System.out.println("11111111111111111111111");
        List<PostTag> postTags = post1.getPostTags();
        System.out.println("2222222222222222222222");
        for (PostTag postTag : postTags) {
            System.out.println("333333333333333333333333");
            Tag tag1 = postTag.getTag();
            System.out.println("444444444444444444444444");
            String content = tag1.getContent();
            System.out.println("555555555555555555555555");
        }
//        Tuple tuple = query.select(post.id, post.title, post.content).from(post).where(post.id.eq(1L)).fetchOne();
//        List<Tuple> fetch = query.select(post.id, post.title, post.content).from(post).fetch();

        System.out.println();
    }

    @Test
    void test3() {
        List<Post> fetch = query
                .select(post)
                .from(post)
                .join(post.postTags, postTag)
                .fetchJoin()
                .join(postTag.tag, tag)
                .fetchJoin()
                .where(
                        post.id.eq(1L)
                )
                .fetch();
        System.out.println();
    }

    @Test
    void test4() {
        List<Post> fetch = query
                .select(post)
                .from(post)
                .join(post.postTags, postTag)
                .fetchJoin()
                .join(postTag.tag, tag)
                .fetchJoin()
                .where(
                        tag.id.eq(3L)
                )
                .fetch();

        System.out.println("fetch = " + fetch);
    }

    @Test
    void test5() {
//        List<Post> fetch = query
//                .selectFrom(post)
//                .join(post.postTags, postTag).fetchJoin()
//                .join(postTag.tag, tag).fetchJoin().fetch();
//
//
//        List<Post> fetch1 = query
//                .selectFrom(post)
//                .join(post.postTags, postTag).fetchJoin()
//                .join(postTag.tag, tag).fetchJoin()
//                .where(tag.id.eq(3L))
//                .fetch();
//
//
//        List<Post> fetch2 = query.selectFrom(post).fetch();

        List<PostTag> fetch3 = query
                .selectFrom(postTag)
                .join(postTag.tag, tag).fetchJoin()
                .join(postTag.post, post).fetchJoin()
                .where(tag.id.eq(3L))
                .fetch();

        List<Post> fetch = query
                .selectFrom(post)
                .join(post.postTags, postTag).fetchJoin()
                .join(postTag.tag, tag).fetchJoin()
                .where(postTag.post.in(
                        select(postTag.post)
                                .from(postTag)
                                .where(postTag.tag.id.eq(3L))
                ))
                .fetch();


        System.out.println();
    }



    private boolean isLoaded(Object obj) {
        return emf.getPersistenceUnitUtil().isLoaded(obj);
    }
}