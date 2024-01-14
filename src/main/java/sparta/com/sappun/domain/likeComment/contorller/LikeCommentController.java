package sparta.com.sappun.domain.likeComment.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.likeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.likeComment.service.LikeCommentService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class LikeCommentController {
    private final LikeCommentService likeCommentService;

    @PostMapping("/{commentId}/like")
    public CommonResponse<LikeCommentSaveRes> likeComment(
            @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userId) {
        return CommonResponse.success(
                likeCommentService.clickLikeComment(commentId, userId.getUser().getId()));
    }
}
