package jylee.blog.app.entity;

import jakarta.persistence.*;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.repository.post_tag.PostTagRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post")//, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> postTags = new ArrayList<>();

    public Post(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();

    }

    public void editPost(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
    }

    public void addPostTagsToPost(PostTag postTag) {
        this.postTags.add(postTag);
    }

    public void clearTags() {
        this.postTags.clear();
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\n' +
                ", id=" + id + '\n' +
                ", content='" + content + '\n' +
                '}';
    }
}
