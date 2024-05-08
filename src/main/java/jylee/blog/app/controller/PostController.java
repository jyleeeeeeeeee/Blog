package jylee.blog.app.controller;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    public static String BASE_MAIN = "base/main";
    @Autowired
    private final PostService postService;

    @GetMapping
    public String list(Model model) {
        List<PostResponse> postList = postService.findAllPost();
        model.addAttribute("postList", postList);

        return addAttributeToModel(model, "post", "post/postList", "post");
    }

    @GetMapping("/new")
    public String write(Model model) {
        return addAttributeToModel(model, "post", "post/postWrite", "write");
    }

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        PostResponse postResponse = postService.findById(id);
        model.addAttribute("post", postResponse);

        return addAttributeToModel(model, "post_wrapper", "post/postView", "post_wrapper");
    }

    @GetMapping("/tag/{id}")
    public String viewByTagId(Model model, @PathVariable("id") Long id) {
        List<PostResponse> postList = postService.findPostAllByTagId(id);
        model.addAttribute("postList", postList);

        return addAttributeToModel(model, "post", "post/postList", "post");
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        PostResponse postResponse = postService.findById(id);
        model.addAttribute("post", postResponse);

        return addAttributeToModel(model, "edit", "post/postEdit", "edit");
    }

    private static String addAttributeToModel(Model model, String post, String view, String fragment) {
        model.addAttribute("title", post);
        model.addAttribute("view", view);
        model.addAttribute("fragment", fragment);;
        return BASE_MAIN;
    }
}
