package jylee.blog.app.repository;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostRepositoryTest {
    @Autowired
    PostRepository repository;

    @Test
    public void save() {
        Post post = new Post(new PostRequest("제목123123", "내용123123"));
        repository.save(post);

        Post findPost = repository.findById(post.getId()).get();
        Assertions.assertThat(findPost).isEqualTo(post);
    }

    @Test
    public void findAll() {

    }
}