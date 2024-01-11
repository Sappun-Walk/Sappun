package sparta.com.sappun.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.request.BoardUpdateReq;
import sparta.com.sappun.domain.board.dto.response.*;
import sparta.com.sappun.domain.board.entity.RegionEnum;
import sparta.com.sappun.domain.board.service.BoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
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
    @GetMapping("/region")
    public CommonResponse<BoardListGetRes> getBoards(@RequestParam RegionEnum region) {
        BoardListGetRes responseDto = boardService.getBoardList(region);
        return CommonResponse.success(responseDto);
    }

    // Best 게시글 조회
    @GetMapping("/best")
    public CommonResponse<BoardBestListGetRes> getBestBoards() {
        return CommonResponse.success(boardService.getBoardBestList());
    }

    // 게시글 작성
    @PostMapping
    public CommonResponse<BoardSaveRes> saveBoard(
            @RequestBody BoardSaveReq boardSaveReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardSaveReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(boardService.saveBoard(boardSaveReq));
    }

    // 게시글 수정
    @PatchMapping("/{boardId}")
    public CommonResponse<BoardUpdateRes> updateBorad(
            @PathVariable Long boardId,
            @RequestBody @Valid BoardUpdateReq boardUpdateReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardUpdateReq.setBoardId(boardId);
        boardUpdateReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(boardService.updateBoard(boardUpdateReq));
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public CommonResponse<BoardDeleteRes> deleteBoard(
            @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(boardService.deleteBoard(boardId, userDetails.getUser()));
    }
}
