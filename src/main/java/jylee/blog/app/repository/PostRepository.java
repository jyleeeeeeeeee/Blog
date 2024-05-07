package jylee.blog.app.repository;

import jylee.blog.app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDSLRepository {
}
