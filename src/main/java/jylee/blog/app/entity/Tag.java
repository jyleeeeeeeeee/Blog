package jylee.blog.app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {
    @Id @GeneratedValue
    @Column(name = "TAG_ID")
    private Long id;

    private String content;


    public Tag(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id + + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
