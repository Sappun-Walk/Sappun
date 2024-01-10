package sparta.com.sappun.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.board.dto.request.BoardListGetReq;
import sparta.com.sappun.domain.board.dto.request.BoardSaveReq;
import sparta.com.sappun.domain.board.dto.response.BoardBestListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardListGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardSaveRes;
import sparta.com.sappun.domain.board.service.BoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시글 단건 조회
    @GetMapping("/{boardId}")
    public CommonResponse<BoardGetRes> getBoard(@PathVariable Long boardId) {
        return CommonResponse.success(boardService.getBoard(boardId));
    }

    //지역별 게시글 조회
    @GetMapping("/region")
    public CommonResponse<BoardListGetRes> getBoards(@RequestBody BoardListGetReq requestDto) {
        BoardListGetRes responseDto = boardService.getBoardList(requestDto.getRegion());
        return CommonResponse.success(responseDto);
    }

    //Best 게시글 조회
    @GetMapping("/best")
    public CommonResponse<BoardBestListGetRes> getBestBoards(){
        return CommonResponse.success(boardService.getBoardBestList());
    }

    //게시글 작성
    @PostMapping
    public CommonResponse<BoardSaveRes> saveBoard(
            @RequestBody BoardSaveReq boardSaveReq,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardSaveReq.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(boardService.saveBoard(boardSaveReq));
    }


}
