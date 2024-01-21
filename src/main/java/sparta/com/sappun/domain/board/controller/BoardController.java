package sparta.com.sappun.domain.board.controller;

import jakarta.validation.Valid;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.board.dto.response.*;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.board.service.BoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Controller
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/create-page")
    @PreAuthorize("isAuthenticated()")
    public String createPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        return "saveBoardPage";
    }

    @GetMapping("/{boardId}")
    public String getBoardDetails(
            @PathVariable Long boardId,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        BoardGetRes boardGetRes = boardService.getBoard(boardId);
        Collections.reverse(boardGetRes.getComments());
        model.addAttribute("board", boardGetRes);
        return "getBoardDetail";
    }

    @GetMapping("/region")
    public String getBoardsByRegion(
            @RequestParam RegionEnum region,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "8") int size,
            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        Page<BoardToListGetRes> responseDto =
                boardService.getBoardList(region, page - 1, size, sortBy, isAsc);
        model.addAttribute("region", region);
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", 5);
        return "regionPage";
    }

    // Best 게시글 조회
    @GetMapping("/best")
    public String getBestBoards(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardBestListGetRes bestBoards = boardService.getBoardBestList();
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        model.addAttribute("bestBoards", bestBoards);
        model.addAttribute("boardList", bestBoards.getBoards());
        return "mainPage";
    }

    // 게시글 작성
    @ResponseBody
    @PostMapping
    public CommonResponse<BoardSaveRes> saveBoard(
            @Valid BoardSaveReq boardSaveReq, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardSaveReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(boardService.saveBoard(boardSaveReq, boardSaveReq.getImage()));
    }

    // 게시글 수정
    @ResponseBody
    @PatchMapping("/{boardId}")
    public CommonResponse<BoardUpdateRes> updateBoard(
            @PathVariable Long boardId,
            @Valid BoardUpdateReq boardUpdateReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardUpdateReq.setBoardId(boardId);
        boardUpdateReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(
                boardService.updateBoard(boardUpdateReq, boardUpdateReq.getImage()));
    }

    @GetMapping("/update/{boardId}")
    public String updateBoardDetails(
            @PathVariable Long boardId,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardUpdateRes boardUpdateRes = boardService.getBoardUpdateRes(boardId);
        model.addAttribute("board", boardUpdateRes);
        model.addAttribute("user", userDetails.getUser());
        return "updateBoardPage";
    }

    // 게시글 삭제
    @ResponseBody
    @DeleteMapping("/{boardId}")
    public CommonResponse<BoardDeleteRes> deleteBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(boardService.deleteBoard(boardId, userDetails.getUser().getId()));
    }
}
