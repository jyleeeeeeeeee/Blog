package jylee.blog.app.repository;


import jylee.blog.app.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByContent(String content);
}
