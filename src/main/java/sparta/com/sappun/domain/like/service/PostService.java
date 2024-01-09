package sparta.com.sappun.domain.like.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.like.dto.request.PostRequestDto;
import sparta.com.sappun.domain.like.dto.response.PostResponseDto;
import sparta.com.sappun.domain.like.entity.Post;
import sparta.com.sappun.domain.like.repository.PostRepository;
import sparta.com.sappun.global.security.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void createPost(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {

        // 예외 처리
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목을 입력하세요.");
        } else if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용을 입력하세요.");
        }

        // 받아온 정보로 post 객체 생성
        Post post = new Post(postRequestDto, userDetails);

        // DB에 저장
        postRepository.save(post);
    }

    public List<PostResponseDto> getPostList() {
        // post 전체 리스트 생성
        List<Post> postList = postRepository.findAll();
        // 반환 타입의 리스트 생성
        List<PostResponseDto> responseDtoList = new ArrayList<>();

        // 반복문을 통해 postList의 내용물을 반환 타입의 리스트에 담은 후 반환
        for (Post post : postList) {
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;
    }

    public PostResponseDto getPost(Long postId) {
        // 해당 게시물의 id와 일치하는지 검증 및 post 객체 생성
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));
        // DTO로 변환 후 반환
        return new PostResponseDto(post);
    }

    // 검증 메서드
    private Post checkPostIdAndUser(Long postId, UserDetailsImpl userDetails) {
        // 해당 id의 게시물이 존재하는지 검증 및 post 객체 생성
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 id의 게시물이 없습니다."));

        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new IllegalArgumentException("게시물 작성자만 수정 및 삭제 가능합니다.");
        }

        return post;
    }
}

