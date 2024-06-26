package jylee.blog.app.dto;

import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private List<PostTag> postTags = new ArrayList<>();
    private Long prevId;
    private Long nextId;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postTags = post.getPostTags();
    }

    public PostResponse setPrevId(Long prevId) {
        this.prevId = prevId;
        return this;
    }

    public PostResponse setNextId(Long nextId) {
        this.nextId = nextId;
        return this;
    }
}
