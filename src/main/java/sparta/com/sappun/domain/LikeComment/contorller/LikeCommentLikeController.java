package sparta.com.sappun.domain.LikeComment.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.LikeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.LikeComment.service.LikeCommentService;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class LikeCommentLikeController {
    private final LikeCommentService likeCommentService;

    @PostMapping("/{commentId}/like")
    public CommonResponse<LikeCommentSaveRes> likeComment(@PathVariable Long commentId,
                                                           @AuthenticationPrincipal UserDetailsImpl userId) {
        return CommonResponse.success(likeCommentService.likeCommentSaveRes(commentId,userId.getUser().getId()));
   }
}
