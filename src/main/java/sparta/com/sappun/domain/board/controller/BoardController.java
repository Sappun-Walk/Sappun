package sparta.com.sappun.domain.board.controller;

import jakarta.validation.Valid;
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

    // 게시글 단건 조회
    @ResponseBody
    @GetMapping("/{boardId}")
    public CommonResponse<BoardGetRes> getBoard(@PathVariable Long boardId) {
        return CommonResponse.success(boardService.getBoard(boardId));
    }

    @GetMapping("/createPage1")
    @PreAuthorize("isAuthenticated()")
    public String createPage1(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        return "saveBoardPage";
    }

    @GetMapping("/details/{boardId}")
    public String getBoardDetails(
            @PathVariable Long boardId,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        // 게시물 상세 정보를 가져오는 로직을 작성하고, Thymeleaf에 필요한 데이터를 Model에 추가
        // 예시로 게시물 ID를 출력하는 부분
        BoardGetRes boardGetRes = boardService.getBoard(boardId);
        model.addAttribute("board", boardGetRes);
        //        model.addAttribute("userId", userDetails.getUser().getId());
        return "getBoardDetail"; // boardDetails는 상세 페이지의 Thymeleaf 템플릿 이름
    }

    // 지역별 게시글 조회
    //    @GetMapping("/region")
    //    public CommonResponse<Page<BoardGetRes>> getBoards(
    //            @RequestParam RegionEnum region,
    //            @RequestParam("page") int page,
    //            @RequestParam("size") int size,
    //            @RequestParam("sortBy") String sortBy,
    //            @RequestParam("isAsc") boolean isAsc) {
    //        Page<BoardGetRes> responseDto =
    //                boardService.getBoardList(region, page - 1, size, sortBy, isAsc);
    //        return CommonResponse.success(responseDto);
    //    }

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
            model.addAttribute("username", userDetails.getUsername());
        }
        Page<BoardGetRes> responseDto =
                boardService.getBoardList(region, page - 1, size, sortBy, isAsc);
        model.addAttribute("region", region.getRegion());
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", 5);
        return "regionPage";
    }
    //     Best 게시글 조회
    //    @GetMapping("/best")
    //    public CommonResponse<BoardBestListGetRes> getBestBoards() {
    //        return CommonResponse.success(boardService.getBoardBestList());
    //    }

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
