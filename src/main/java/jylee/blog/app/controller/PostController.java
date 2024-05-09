package jylee.blog.app.controller;

import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public static final String BASE_MAIN = "base/main";
    @GetMapping
    public String list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            Model model) {
        if(page < 1) {
            page = 1;
        }

        Page<PostResponse> posts = postService.findAllPost(page, size, sort);
        CustomPage pageInfo = new CustomPage(posts);
        Paging paging = createPaging(pageInfo);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("paging", paging);

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
    public String viewByTagId(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            Model model, @PathVariable("id") Long id) {
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
        model.addAttribute("fragment", fragment);
        return BASE_MAIN;
    }

    private Paging createPaging(CustomPage page) {
        int back = 0;
        int[] previous = null;
        int previousSize = 0;
        int present = page.getNumber();
        int[] after = null;
        int afterSize = 0;
        int forward = 0;

        int gap = 2;

        int totalPages = page.getTotalPages();
        if (!page.isEmpty()) {
            if (present >= gap + 1) {
                previousSize = gap;
                back = present - gap - 1;
            } else {
                previousSize = present - 1;
            }
            previous = new int[previousSize];
            for(int i = 0; i < previousSize; i++) {
                previous[i] = present - previousSize + i;
            }

            if(totalPages > gap * 2 + 1) {
                if(totalPages - present > gap) {
                    afterSize = gap;
                    forward = present + gap + 1;
                } else {
                    afterSize = totalPages - present;
                }
                after = new int[afterSize];
                for(int i = 0; i < afterSize; i++) {
                    after[i] = present + i + 1;
                }
            }
        }

        return new Paging(back, previous, present, after, forward);
    }
}
