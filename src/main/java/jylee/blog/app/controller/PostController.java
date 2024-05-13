package jylee.blog.app.controller;

import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.entity.Tag;
import jylee.blog.app.repository.post.PostRepository;
import jylee.blog.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final PostRepository postRepository;

    @GetMapping
    public String list(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            Model model) {
        if (page < 1) {
            page = 1;
        }

        Page<PostResponse> posts = postService.findAllPost(page, size, sort);
        CustomPage pageInfo = new CustomPage(posts);
        Paging paging = createPaging(pageInfo, "/post");

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("paging", paging);

        return addAttributeToModel(model, "post", "post/postList", "post", "");
    }

    @GetMapping("/new")
    public String write(Model model) {
        return addAttributeToModel(model, "post", "post/postWrite", "write", "");
    }

    @GetMapping("/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        PostResponse postResponse = postService.findById(id);

        model.addAttribute("post", postResponse);
        model.addAttribute("url", "/post");
        return addAttributeToModel(model, "post_wrapper", "post/postView", "post_wrapper", "");
    }

    @GetMapping("/tag/{id}")
    public String viewByTagId(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            Model model, @PathVariable("id") Long id) {
        if (page < 1) {
            page = 1;
        }

        Page<PostResponse> posts = postService.findPostAllByTagId(page, size, sort, id);
        CustomPage pageInfo = new CustomPage(posts);
        Paging paging = createPaging(pageInfo, "/post/tag/" + id);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("paging", paging);

        return addAttributeToModel(model, "post", "post/postList", "post", "");
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            Model model) {
        if (page < 1) {
            page = 1;
        }

        Page<PostResponse> posts = postService.findPostByKeyword(page, size, sort, keyword);
        CustomPage pageInfo = new CustomPage(posts);
        Paging paging = createPaging(pageInfo, "/post/search/" + keyword);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("paging", paging);

        return addAttributeToModel(model, "post", "post/postList", "post", "");
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        Post post = postRepository.findPostFetchById(id);
        PostResponse postResponse = new PostResponse(post);
        model.addAttribute("post", postResponse);

        return addAttributeToModel(model, "edit", "post/postEdit", "edit", "");
    }

    private static String addAttributeToModel(Model model, String post, String view, String fragment, String keyword) {
        model.addAttribute("title", post);
        model.addAttribute("view", view);
        model.addAttribute("fragment", fragment);
        model.addAttribute("keyword", keyword);
        return BASE_MAIN;
    }

    private Paging createPaging(CustomPage page, String url) {
        long back = 0;
        long[] previous = null;
        int previousSize = 0;
        long present = page.getNumber();
        long[] after = null;
        int afterSize = 0;
        long forward = 0;

        int gap = 2;

        int totalPages = page.getTotalPages();
        if (!page.isEmpty()) {
            if (present >= gap + 1) {
                previousSize = gap;
                back = present - gap - 1;
            } else {
                previousSize = (int) present - 1;
            }
            previous = new long[previousSize];
            for (int i = 0; i < previousSize; i++) {
                previous[i] = present - previousSize + i;
            }

            if (totalPages - present > gap) {
                afterSize = gap;
                forward = present + gap + 1;
            } else {
                afterSize = totalPages - (int) present;
            }
            after = new long[afterSize];
            for (int i = 0; i < afterSize; i++) {
                after[i] = present + i + 1;
            }
        }

        return new Paging(back, previous, present, after, forward, url);
    }
}
