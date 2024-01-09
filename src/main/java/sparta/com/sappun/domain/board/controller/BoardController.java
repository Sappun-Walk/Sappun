package sparta.com.sappun.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.board.dto.request.BoardListGetReq;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;
import sparta.com.sappun.domain.board.dto.response.BoardListGetRes;
import sparta.com.sappun.domain.board.service.BoardService;
import sparta.com.sappun.global.response.CommonResponse;

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
    public CommonResponse<List<BoardListGetRes>> getBoards(@RequestBody BoardListGetReq requestDto){
        List<BoardListGetRes> responseDto = boardService.getBoardList(requestDto);
        return CommonResponse.success(responseDto);
    }

    //Best 게시글 조회 (미구현)



}
