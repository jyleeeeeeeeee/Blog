package jylee.blog.app.service;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PortfolioService {
    private final PostRepository repository;

    // 게시물 작성
    @Transactional
    public Long savePost(PostRequest postRequest) {
        Post post = new Post(postRequest);
        return repository.save(post).getId();
    }

    // 게시물 단건 조회
    public PostResponse findById(Long id) {
        Post post = repository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new PostResponse(post);
    }

    // 게시물 모두 조회
    public List<PostResponse> findAllPost() {
        return repository.findAll().stream().map(PostResponse::new).collect(Collectors.toList());
    }
}
