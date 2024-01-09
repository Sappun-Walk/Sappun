package sparta.com.sappun.domain.like.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.like.dto.request.PostRequestDto;
import sparta.com.sappun.domain.like.dto.response.CommonResponseDto;
import sparta.com.sappun.domain.like.dto.response.PostResponseDto;
import sparta.com.sappun.domain.like.service.LikePostService;
import sparta.com.sappun.domain.like.service.PostService;
import sparta.com.sappun.global.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final LikePostService postLikesService;

    @PostMapping
    public ResponseEntity<CommonResponseDto> createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            postService.createPost(requestDto, userDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().body(new CommonResponseDto("게시물 등록 완료",HttpStatus.OK.value()));
    }

    @GetMapping
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto> getPost(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok().body(postService.getPost(postId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("/{postId}/likes")
    public ResponseEntity<CommonResponseDto> toggleLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            boolean liked = postLikesService.toggleLike(postId, userDetails);

            if (liked) {
                return ResponseEntity.ok().body(new CommonResponseDto("좋아요!", HttpStatus.OK.value()));
            } else {
                return ResponseEntity.ok().body(new CommonResponseDto("좋아요 취소!", HttpStatus.OK.value()));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}