package jylee.blog.app.repository.mybatis;

import jylee.blog.app.dto.PostView;

@org.apache.ibatis.annotations.Mapper
public interface Mapper {
    Long countByKeyword(String keyword);
    PostView postView(Long id);
}
