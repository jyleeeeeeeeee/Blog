package jylee.blog.app.repository;

import jylee.blog.app.entity.Post;

import java.util.List;

public interface PostQueryDSLRepository {
    Post findPostById(Long id);
    List<Post> findPostAll();
    List<Post> findPostAllByTagId(Long id);
}
