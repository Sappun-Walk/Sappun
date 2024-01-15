package sparta.com.sappun.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.request.CommentUpdateReq;
import sparta.com.sappun.domain.comment.dto.response.CommentDeleteRes;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.dto.response.CommentUpdateRes;
import sparta.com.sappun.domain.comment.service.CommentService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/comments")
    public CommonResponse<CommentSaveRes> saveComment(
            @PathVariable Long boardId,
            @RequestBody @Valid CommentSaveReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // TODO: 댓글 사진 입력받기
        req.setBoardId(boardId);
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.saveComment(req));
    }

    @PatchMapping("/comments/{commentId}")
    public CommonResponse<CommentUpdateRes> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentUpdateReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // TODO: 댓글 수정사진 입력받기
        req.setCommentId(commentId);
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.updateComment(req));
    }

    @DeleteMapping("/comments/{commentId}")
    public CommonResponse<CommentDeleteRes> deleteComment(
            @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(
                commentService.deleteComment(commentId, userDetails.getUser().getId()));
    }
}
