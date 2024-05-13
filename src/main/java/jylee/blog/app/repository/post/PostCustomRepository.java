package jylee.blog.app.repository.post;

import jylee.blog.app.entity.Post;

import java.util.List;

public interface PostCustomRepository {
    Post findPostFetchById(Long id);
    List<Post> findPostAll(Long offset, int size, String sort);
    List<Post> findPostAllByTagId(Long offset, int size, String sort, Long id);
    List<Post> findPostByKeyword(Long offset, int size, String sort, String keyword);
    Long findPrevPostId(Long id);
    Long findNextPostId(Long id);

}
