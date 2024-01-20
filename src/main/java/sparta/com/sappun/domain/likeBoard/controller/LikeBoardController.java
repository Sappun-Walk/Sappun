package sparta.com.sappun.domain.likeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.likeBoard.service.LikeBoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class LikeBoardController {
    private final LikeBoardService likeBoardService;

    @PostMapping("/{boardId}/like")
    public CommonResponse<LikeBoardSaveRes> likeBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(
                likeBoardService.clickLikeBoard(boardId, userDetails.getUser().getId()));
    }
}
