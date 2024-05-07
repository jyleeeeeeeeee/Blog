package jylee.blog.app.entity;

import jakarta.persistence.*;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "POST_TAG")
public class PostTag {
    @Id
    @GeneratedValue
    @Column(name = "POST_TAG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TAG_ID")
    private Tag tag;


    public PostTag(Tag tag, Post post) {
        this.tag = tag;
        this.post = post;
    }

    @Override
    public String toString() {
        return "PostTag{" +
                "id=" + id + '\'' +
                ", post=" + post + '\'' +
                ", tag=" + tag + '\n' +
                '}';
    }
}
