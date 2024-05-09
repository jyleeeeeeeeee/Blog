package jylee.blog.app.repository.post;

import jylee.blog.app.entity.Post;

import java.util.List;

public interface PostCustomRepository {
    Post findPostFetchById(Long id);
    List<Post> findPostAll(Long page, int size, String sort);
    List<Post> findPostAllByTagId(Long id);
}
