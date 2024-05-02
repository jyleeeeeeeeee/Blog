package jylee.blog.app.controller;

import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.repository.PostRepository;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @Autowired
    private final PostService postService;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "About Me");
        model.addAttribute("view", "base/home");
        model.addAttribute("fragment", "home");
        return "base/main";
    }

    @GetMapping("/aboutme")
    public String aboutMe(Model model) {
        model.addAttribute("title", "About Me");
        model.addAttribute("view", "base/aboutMe");
        model.addAttribute("fragment", "aboutMe");
        return "base/main";
    }

    @GetMapping("/write")
    public String post(Model model) {
        model.addAttribute("title", "post");
        model.addAttribute("view", "post/postWrite");
        model.addAttribute("fragment", "post");
        return "base/main";
    }

    @GetMapping("/post")
    public String posts(Model model) {
        List<PostResponse> postList = postService.findAllPost();
        model.addAttribute("postList", postList);
        model.addAttribute("title", "post");
        model.addAttribute("view", "post/postList");
        model.addAttribute("fragment", "post");
        return "base/main";
    }

    @GetMapping("/post/{id}")
    public String posts(Model model, @PathVariable("id") Long id) {
        PostResponse postResponse = postService.findById(id);
        model.addAttribute("post", postResponse);
        model.addAttribute("title", "post_wrapper");
        model.addAttribute("view", "post/postView");
        model.addAttribute("fragment", "post_wrapper");
        return "base/main";
    }


}
