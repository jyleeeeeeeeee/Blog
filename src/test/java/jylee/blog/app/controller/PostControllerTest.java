package jylee.blog.app.controller;

import jakarta.persistence.EntityManager;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.entity.Tag;
import jylee.blog.app.repository.tag.TagRepository;
import jylee.blog.app.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@Transactional
class PostControllerTest {
    @Autowired
    EntityManager em;
    @Autowired
    TagRepository tagRepository;

    @Autowired
    PostService postService;

    public static final int FIRST = 1;

    @Test
    void test() {
        init(113);
        ArrayList<String> strings = new ArrayList<>();
        for(int i = 1; i < 20; i++) {
            strings.add(print(getPageaData(i)));
        }
        
        for(String s : strings) {
            System.out.println("s = " + s);
        }
    }

    private Paging getPageaData(int page) {
//        int page = 8;
        int size = 10;
        String sort = "asc";
        if (page < 1) {
            page = 1;
        }

        Page<PostResponse> allPost = postService.findAllPost(page, size, sort);
        CustomPage pageInfo = new CustomPage(allPost);

        int back = 0;
        int[] previous = null;
        int previousSize = 0;
        int present = pageInfo.getNumber();
        int[] after = null;
        int afterSize = 0;
        int forward = 0;

        int gap = 2;

        int totalPages = pageInfo.getTotalPages();
        if (!pageInfo.isEmpty()) {
            if (present >= gap + 1) {
                previousSize = gap;
                back = present - gap - 1;
            } else {
                previousSize = present - 1;
            }
            previous = new int[previousSize];
            for(int i = 0; i < previousSize; i++) {
                previous[i] = present - previousSize + i;
            }

            ////////////////////////////////////////////////////////////////

            if(totalPages == present) {
                System.out.println("totalPages = " + totalPages);
                System.out.println("totalPages = " + totalPages);
            }
            if(totalPages > gap * 2 + 1) {
                if(totalPages - present > gap) {
                    afterSize = gap;
                    forward = present + gap + 1;
                } else {
                    afterSize = totalPages - present;
                }
                after = new int[afterSize];
                for(int i = 0; i < afterSize; i++) {
                    after[i] = present + i + 1;
                }
            }
        }

        return new Paging(back, previous, present, after, forward);
    }

    public String print(Paging pageData){
        String str = "";
        str += pageData.getBack()!= 0? "< " : "";
        str += Arrays.toString(pageData.getPrevious());
        str += pageData.getPresent();
        str += Arrays.toString(pageData.getAfter());
        str += pageData.getForward()!= 0? " >" : "";
        return str;
    }

    public int getIndex(int index) {
        return index - 1;
    }

    //    @BeforeEach
    public void init(int tuples) {
        if (tuples == 0) {
            return;
        }
        for (int i = 0; i < tuples; i++) {
            Post post = new Post(new PostRequest(i + "번 게시글 제목", "<p>저장 테스트입니다.</p><p><br></p><p>ㅋㅋ</p><p><br></p><p>ㄴㅇㄹㅁㄴㅇㄹ</p><p>ㄴㅁㅇㄹㄴㅁㅇㄹ</p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p><p><br></p><p><img src=\"/tui-editor/image-print?filename=64eec421-dbf6-487a-a666-474da9ccba4a.jpeg\" alt=\"DSC_1663.jpeg\" contenteditable=\"false\"><br></p><p><br></p><p>ㅁㄴㅇㄹㄴㅁㅇㄹ</p>",
                    new ArrayList<>()));
            em.persist(post);

            for (int j = 1; j < 10; j++) {
                int per = i % j;
                if (per == 0) {
                    Tag tag = new Tag("태그" + j);
                    if (!tagRepository.existsByContent(tag.getContent())) {
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