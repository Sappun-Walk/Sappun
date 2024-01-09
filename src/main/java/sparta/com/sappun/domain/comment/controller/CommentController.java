package sparta.com.sappun.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.service.CommentService;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @PostMapping
    public CommonResponse<CommentSaveRes> saveComment(
            @RequestBody CommentSaveReq commentSaveReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // TODO: 댓글 사진 입력받기
        commentSaveReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(commentService.saveComment(commentSaveReq));
    }
}
