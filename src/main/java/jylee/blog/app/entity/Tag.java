package jylee.blog.app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {
    @Id @GeneratedValue
    @Column(name = "TAG_ID")
    private Long id;

    private String content;


    @OneToMany(mappedBy = "tag")
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String content) {
        this.content = content;
    }

    public void setPostToTag(PostTag postTag) {
        this.postTags.add(postTag);
    }
}
