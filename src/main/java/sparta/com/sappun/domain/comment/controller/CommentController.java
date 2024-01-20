package sparta.com.sappun.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @ResponseBody
    @PostMapping("/{boardId}/comments")
    public CommonResponse<CommentSaveRes> saveComment(
            @PathVariable Long boardId,
            @RequestPart(name = "data") @Valid CommentSaveReq req,
            @RequestPart(name = "image", required = false) MultipartFile multipartfile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setBoardId(boardId);
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.saveComment(req, multipartfile));
    }

    @PatchMapping("/comments/{commentId}")
    public CommonResponse<CommentUpdateRes> updateComment(
            @PathVariable Long commentId,
            @Valid CommentUpdateReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setCommentId(commentId);
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.updateComment(req, req.getImage()));
    }

    @DeleteMapping("/comments/{commentId}")
    public CommonResponse<CommentDeleteRes> deleteComment(
            @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(
                commentService.deleteComment(commentId, userDetails.getUser().getId()));
    }
}
