package jylee.blog.app.service;

import jylee.blog.app.dto.PostRequest;
import jylee.blog.app.dto.PostResponse;
import jylee.blog.app.entity.Post;
import jylee.blog.app.entity.PostTag;
import jylee.blog.app.entity.Tag;
import jylee.blog.app.repository.post.PostRepository;
import jylee.blog.app.repository.post_tag.PostTagRepository;
import jylee.blog.app.repository.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        postRepository.save(post);

        // 태그 생성, 등록
        createAndAddTags(postRequest.getTags(), post);
        return post.getId();
    }

    // 게시물 단건 조회
    public PostResponse findById(Long id) {
        Post post = postRepository.findPostFetchById(id);
        return new PostResponse(post);
    }

    // 게시물 모두 조회
    public Page<PostResponse> findAllPost(int page, int size, String sort) {
        PageRequest pageRequest = PageRequest.of(page - 1, size,
                Sort.by(Sort.Direction.fromString(sort), "id"));
        long offset = pageRequest.getOffset();

        List<PostResponse> posts = postRepository.findPostAll(offset, size, sort).stream().map(PostResponse::new).collect(Collectors.toList());
        long count = postRepository.count();
        return new PageImpl<>(posts, pageRequest, count);
    }

    // 게시물 모두 조회
//    public Page<PostResponse> findAllPostPaging(PageRequest pageRequest) {
//        List<PostResponse> collect = postRepository.findPostAll(pageRequest).stream().map(PostResponse::new).collect(Collectors.toList());
//        return collect;
//    }

    // 태그로 게시물 조회
    public List<PostResponse> findPostAllByTagId(Long id) {
        return postRepository.findPostAllByTagId(id).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    // 게시물 수정
    @Transactional
    public Long editPost(PostRequest postRequest) {
        Post post = postRepository.findPostFetchById(postRequest.getId());

        // Post title, content 수정
        post.editPost(postRequest);

        // 등록된 태그 모두 삭제
        Long postId = post.getId();
        post.clearTags();
        postTagRepository.deleteByPostId(postId);

        // 태그 생성, 등록
        createAndAddTags(postRequest.getTags(), post);
        return postId;
    }

    private void createAndAddTags(List<Tag> tags, Post post) {
        tags.forEach(tag -> {
            // DB에 태그가 존재하는지 확인
            boolean exist = tagRepository.existsByContent(tag.getContent());
            if(!exist) {
                // DB에 태그가 존재하지 않으면 새로 생성
                tagRepository.save(tag);
            } else {
                // DB에 태그가 존재하면 가져오기
                tag = tagRepository.findByContentEquals(tag.getContent());
            }

            // PostTag 생성
            PostTag postTag = new PostTag(tag, post);
            postTagRepository.save(postTag);
            post.addPostTagsToPost(postTag);
        });
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.delete(postRepository.findById(id).orElseThrow());
    }
}
