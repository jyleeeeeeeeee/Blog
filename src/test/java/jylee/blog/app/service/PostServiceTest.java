package jylee.blog.app.service;

import jylee.blog.app.controller.CustomPage;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.repository.post.PostRepository;
import jylee.blog.app.repository.post_tag.PostTagRepository;
import jylee.blog.app.repository.tag.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.entity.Tag;
import jylee.blog.app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private PostService postService;

    @Test
    public void editPost_shouldUpdatePostTitleContentAndTags() {
        // Given
        Long postId = 1L;
        PostRequest postRequest = new PostRequest(1L, "Updated Title", "Updated Content", List.of(new Tag("updatedTag")));
        Post post = new Post(postRequest);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(tagRepository.existsByContent(any())).thenReturn(true);
        when(tagRepository.findByContentEquals(any())).thenReturn(new Tag("updatedTag"));


        // When
        postTagRepository.deleteByPostId(postId);
        Long updatedPostId = postService.editPost(postRequest);

        // Then
        assertEquals(postId, updatedPostId);
        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Content", post.getContent());
        assertEquals(1, post.getPostTags().size());
        assertEquals("updatedTag", post.getPostTags().get(0).getTag().getContent());
    }

    @Test
    void countTest() {
        long count = postRepository.count();
        System.out.println("count = " + count);
    }

    @Test
    void test() {
        Page<PostResponse> posts = postService.findAllPost(1, 10, "desc");
        CustomPage page = new CustomPage(posts);
        System.out.println("allPost = " + posts);
    }
}