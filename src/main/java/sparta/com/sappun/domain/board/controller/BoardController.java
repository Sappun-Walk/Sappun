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

    @GetMapping("/boardcreate1")
    public String boardcreate() {
        return "saveBoardPage1";
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
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Page<BoardGetRes> responseDto =
                boardService.getBoardList(region, page - 1, size, sortBy, isAsc);
        //1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        int nowPage = responseDto.getPageable().getPageNumber() + 1;
        //-1값이 들어가는 것을 막기 위해서 max값으로 두 개의 값을 넣고 더 큰 값을 넣어주게 된다.
        int startPage =  Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, responseDto.getTotalPages());

        model.addAttribute("responseDto", responseDto);

        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
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

    @GetMapping("/details/{boardId}")
    public String getBoardDetails(@PathVariable Long boardId, Model model) {
        // 게시물 상세 정보를 가져오는 로직을 작성하고, Thymeleaf에 필요한 데이터를 Model에 추가
        // 예시로 게시물 ID를 출력하는 부분
        model.addAttribute("boardId", boardId);
        return "getBoardDetail"; // boardDetails는 상세 페이지의 Thymeleaf 템플릿 이름
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
