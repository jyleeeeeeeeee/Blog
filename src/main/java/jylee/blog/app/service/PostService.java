package jylee.blog.app.service;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.entity.Tag;
import jylee.blog.app.repository.PostRepository;
import jylee.blog.app.repository.PostTagRepository;
import jylee.blog.app.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    // 게시물 작성
    @Transactional
    public Long savePost(PostRequest postRequest) {
        // 포스트 내용 가져오기
        Post post = new Post(postRequest);



        // 태그 가져오기
        List<Tag> tags = postRequest.getTags();

        tags.forEach(tag -> {
            //  DB에 태그가 존재하는지 확인
            boolean exist = tagRepository.existsByContent(tag.getContent());
            if(!exist) {
//                 DB에 태그가 존재하지 않으면 새로 생성
                tagRepository.save(tag);
            }

            PostTag postTag = new PostTag(tag, post);
            post.setTagToPost(postTag);
            tag.setPostToTag(postTag);

            postTagRepository.save(postTag);
        });


        Post save = postRepository.save(post);
        return save.getId();
    }

    // 게시물 단건 조회
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new PostResponse(post);
    }

    // 게시물 모두 조회
    public List<PostResponse> findAllPost() {
        return postRepository.findAll().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    // 게시물 수정
    @Transactional
    public void editPost(PostResponse postResponse) {
        Post post = postRepository.findById(postResponse.getId()).orElseThrow();
        post.editPost(postResponse);
    }


    @Transactional
    public void deletePost(Long id) {
        postRepository.delete(postRepository.findById(id).orElseThrow());
    }
}
