package jylee.blog.app.repository.post_tag;

import jylee.blog.app.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<PostTag, Long>, PostTagCustomRepository{

}
