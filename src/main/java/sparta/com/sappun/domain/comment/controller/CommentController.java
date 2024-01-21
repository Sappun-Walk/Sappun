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
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ResponseBody
    @PostMapping()
    public CommonResponse<CommentSaveRes> saveComment(
            @Valid CommentSaveReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.saveComment(req, req.getImage()));
    }

    @PatchMapping("/{commentId}")
    public CommonResponse<CommentUpdateRes> updateComment(
            @PathVariable Long commentId,
            @Valid CommentUpdateReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setCommentId(commentId);
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.updateComment(req, req.getImage()));
    }

    @DeleteMapping("/{commentId}")
    public CommonResponse<CommentDeleteRes> deleteComment(
            @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(
                commentService.deleteComment(commentId, userDetails.getUser().getId()));
    }
}
