package jylee.blog.app.controller;

import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    @Autowired
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<PostResponse> postList = postService.findAllPost();
        model.addAttribute("postList", postList);
        model.addAttribute("title", "post");
        model.addAttribute("view", "post/postList");
        model.addAttribute("fragment", "post");
        return "base/main";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("title", "post");
        model.addAttribute("view", "post/postWrite");
        model.addAttribute("fragment", "write");
        return "base/main";
    }

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        PostResponse postResponse = postService.findById(id);
        model.addAttribute("post", postResponse);
        model.addAttribute("title", "post_wrapper");
        model.addAttribute("view", "post/postView");
        model.addAttribute("fragment", "post_wrapper");
        return "base/main";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        PostResponse postResponse = postService.findById(id);
        model.addAttribute("post", postResponse);
        model.addAttribute("title", "edit");
        model.addAttribute("view", "post/postEdit");
        model.addAttribute("fragment", "edit");
        return "base/main";
    }

}
