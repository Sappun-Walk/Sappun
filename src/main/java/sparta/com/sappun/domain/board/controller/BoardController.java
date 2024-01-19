package sparta.com.sappun.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @GetMapping("/{boardId}")
    public CommonResponse<BoardGetRes> getBoard(@PathVariable Long boardId) {
        return CommonResponse.success(boardService.getBoard(boardId));
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
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            Model model) {
        Page<BoardGetRes> responseDto =
                boardService.getBoardList(region, page - 1, size, sortBy, isAsc);
        model.addAttribute("responseDto", responseDto);
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
            model.addAttribute("username", userDetails.getUsername());
        }
        model.addAttribute("bestBoards", bestBoards);
        model.addAttribute("boardList", bestBoards.getBoards());
        return "mainPage";
    }
    // 게시글 작성
    @ResponseBody
    @PostMapping
    public CommonResponse<BoardSaveRes> saveBoard(
            @RequestPart(name = "data") @Valid BoardSaveReq boardSaveReq,
            @RequestPart(name = "image", required = false) MultipartFile multipartfile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardSaveReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(boardService.saveBoard(boardSaveReq, multipartfile));
    }

    // 게시글 수정
    @PatchMapping("/{boardId}")
    public CommonResponse<BoardUpdateRes> updateBoard(
            @PathVariable Long boardId,
            @RequestPart(name = "data") @Valid BoardUpdateReq boardUpdateReq,
            @RequestPart(name = "image", required = false) MultipartFile multipartfile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardUpdateReq.setBoardId(boardId);
        boardUpdateReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(boardService.updateBoard(boardUpdateReq, multipartfile));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public CommonResponse<BoardDeleteRes> deleteBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(boardService.deleteBoard(boardId, userDetails.getUser().getId()));
    }
}
