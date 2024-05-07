package jylee.blog.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jylee.blog.app.dto.PostRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Portfolio {
    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String content;

    public Portfolio(PostRequest postRequest) {
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
    }
}
