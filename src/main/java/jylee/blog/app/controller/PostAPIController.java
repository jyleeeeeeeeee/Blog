package jylee.blog.app.controller;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostAPIController {
    private final PostService postService;

    @PostMapping
    public Long savePost(@RequestBody PostRequest param) {
        return postService.savePost(param);
    }

    @PutMapping
    public Long editPost(@RequestBody PostRequest postRequest) {
        return postService.editPost(postRequest);
    }

}
