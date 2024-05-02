package jylee.blog;

import jakarta.annotation.PostConstruct;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.entity.Post;
import jylee.blog.app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class BlogApplication {
    private final PostRepository postRepository;
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Post post = new Post(new PostRequest("1번 게시글 제목","<p>zxcvzxcv</p>"));
        postRepository.save(post);
    }

}
