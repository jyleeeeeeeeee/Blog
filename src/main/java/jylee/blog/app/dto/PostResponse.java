package jylee.blog.app.dto;

import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private List<PostTag> postTags = new ArrayList<>();

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postTags = post.getPostTags();
    }
}
