package jylee.blog.app.repository.post_tag;

import jylee.blog.app.entity.PostTag;

import java.util.List;

public interface PostTagCustomRepository {
    List<PostTag> findByPostId(Long id);
    Long deleteByPostId(Long id);
    Long countByTagId(Long id);
    Long countByKeyword(String keyword);
}
