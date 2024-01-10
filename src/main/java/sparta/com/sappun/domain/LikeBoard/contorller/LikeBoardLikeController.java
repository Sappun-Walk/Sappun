package sparta.com.sappun.domain.LikeBoard.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.com.sappun.domain.LikeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.LikeBoard.service.LikeBoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards")
public class LikeBoardLikeController {
    private final LikeBoardService likeBoardService;


    @PostMapping("/{boardId}/like")
    public CommonResponse<LikeBoardSaveRes> likeBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userId) {
        return CommonResponse.success(
                likeBoardService.likeBoardSaveRes(boardId, userId.getUser().getId()));
    }
}
