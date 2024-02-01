package sparta.com.sappun.domain.likeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.likeBoard.service.LikeBoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/boards")
public class LikeBoardController {

    @Autowired private final LikeBoardService likeBoardService;

    @ResponseBody
    @PostMapping("/{boardId}/like")
    public CommonResponse<LikeBoardSaveRes> likeBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(
                likeBoardService.clickLikeBoard(boardId, userDetails.getUser().getId()));
    }
}
