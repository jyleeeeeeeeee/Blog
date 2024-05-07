package jylee.blog.app.dto;

import jylee.blog.app.entity.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String content;
    private List<Tag> tags;
}
