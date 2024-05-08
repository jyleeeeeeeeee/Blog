package jylee.blog.app.repository;

import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.repository.post.PostRepository;
import jylee.blog.app.repository.post_tag.PostTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class PostTagRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostTagRepository postTagRepository;

    @Test
    void test() {

        long size = postTagRepository.count();
        assertThat(size).isEqualTo(50);

        List<PostTag> byPostId = postTagRepository.findByPostId(2L);
        assertThat(byPostId.size()).isEqualTo(2);

        Long deletedCount = postTagRepository.deleteByPostId(2L);
        assertThat(deletedCount).isEqualTo(2L);

        assertThat(postTagRepository.existsById(2L)).isFalse();
        assertThat(postTagRepository.existsById(3L)).isFalse();

    }


    @Test
    void test2() {
        Post postFetchById = postRepository.findPostFetchById(2L);
        Long postId = postFetchById.getId();

        Long deletedCount = postTagRepository.deleteByPostId(postId);

        assertThat(deletedCount).isEqualTo(2L);

        assertThat(postTagRepository.existsById(2L)).isFalse();
        assertThat(postTagRepository.existsById(3L)).isFalse();

    }
}