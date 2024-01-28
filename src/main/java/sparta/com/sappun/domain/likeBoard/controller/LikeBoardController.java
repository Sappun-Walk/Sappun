package sparta.com.sappun.domain.likeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardGetRes;
import sparta.com.sappun.domain.likeBoard.dto.response.LikeBoardSaveRes;
import sparta.com.sappun.domain.likeBoard.service.LikeBoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/boards")
public class LikeBoardController {

    @Autowired private final LikeBoardService likeBoardService;

    private static final String DEFAULT_PAGE = "1";
    private static final Integer MAX_PAGE = 5;
    private static final String DEFAULT_SIZE = "8";
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final String DEFAULT_IS_ASC = "false";

    @PostMapping("/{boardId}/like")
    public CommonResponse<LikeBoardSaveRes> likeBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(
                likeBoardService.clickLikeBoard(boardId, userDetails.getUser().getId()));
    }

    @GetMapping("/like")
    public String getLikeBoardList(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAsc", defaultValue = DEFAULT_IS_ASC) boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        Page<LikeBoardGetRes> responseDto =
                likeBoardService.getLikeBoardList(page - 1, size, sortBy, isAsc);
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", MAX_PAGE);
        return "likeBoardPage";
    }
}
