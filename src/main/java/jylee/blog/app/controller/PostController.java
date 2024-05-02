package jylee.blog.app.controller;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService service;

    @PostMapping
    public Long savePost(@RequestBody PostRequest param) {
        return service.savePost(param);
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<PostResponse> findAllPost() {
        return service.findAllPost();
    }
}
