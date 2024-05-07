package jylee.blog;

import jakarta.annotation.PostConstruct;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.entity.Post;
import jylee.blog.app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class Initializer {

    @Autowired
    private final PostRepository postRepository;

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 99; i++) {
            Post post = new Post(new PostRequest(i + 1 + "번 게시글 제목","<p>저장 테스트입니다.</p><p><br></p><p>ㅋㅋ</p><p><br></p><p>ㄴㅇㄹㅁㄴㅇㄹ</p><p>ㄴㅁㅇㄹㄴㅁㅇㄹ</p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p><p><br></p><p><img src=\"/tui-editor/image-print?filename=64eec421-dbf6-487a-a666-474da9ccba4a.jpeg\" alt=\"DSC_1663.jpeg\" contenteditable=\"false\"><br></p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p>",
                    new ArrayList<>()));
            postRepository.save(post);
        }
    }
}
