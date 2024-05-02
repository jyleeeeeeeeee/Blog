package jylee.blog.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jylee.blog.app.dto.PostRequest;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    public Post(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
    }
}
