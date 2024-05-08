package jylee.blog.app.repository.post;

import jylee.blog.app.entity.Post;

import java.util.List;

public interface PostCustomRepository {
    Post findPostFetchById(Long id);
    List<Post> findPostAll();
    List<Post> findPostAllByTagId(Long id);
}
