package jylee.blog;

import jakarta.persistence.EntityManager;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.entity.Tag;
import jylee.blog.app.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing
public class BlogApplication {

    private final EntityManager em;
    private final TagRepository tagRepository;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }


    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init() {
        for (int i = 1; i < 999; i++) {
            Post post = new Post(new PostRequest(i + "번 게시글 제목", "<p>저장 테스트입니다.</p><p><br></p><p>ㅋㅋ</p><p><br></p><p>ㄴㅇㄹㅁㄴㅇㄹ</p><p>ㄴㅁㅇㄹㄴㅁㅇㄹ</p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p><p><br></p><p><img src=\"/tui-editor/image-print?filename=64eec421-dbf6-487a-a666-474da9ccba4a.jpeg\" alt=\"DSC_1663.jpeg\" contenteditable=\"false\"><br></p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p>",
                    new ArrayList<>()));
            em.persist(post);

            for(int j = 1; j < 10; j++){
                int per = i % j;
                if(per == 0) {
                    Tag tag = new Tag("태그" + j);
                    if(!tagRepository.existsByContent(tag.getContent())) {
                        em.persist(tag);
                    } else {
                        tag = tagRepository.findByContentEquals(tag.getContent());
                    }

                    PostTag postTag = new PostTag(tag, post);
                    post.addPostTagsToPost(postTag);
                    em.persist(postTag);
                    em.flush();
                    em.clear();
                }
            }
        }
    }

}
