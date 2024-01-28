package sparta.com.sappun.domain.board.controller;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
import sparta.com.sappun.domain.comment.dto.response.CommentGetRes;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Controller
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SIZE = "8";
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final String DEFAULT_IS_ASC = "false";

    // 게시글 작성 페이지
    @GetMapping("/create-page")
    @PreAuthorize("isAuthenticated()")
    public String createPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        return "saveBoardPage";
    }

    // 게시글 상세 조회 페이지
    @GetMapping("/{boardId}")
    public String getBoardDetails(
            @PathVariable Long boardId,
            Model model,
            @RequestParam(value = "sortOrder", defaultValue = "likes") String sortOrder,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        BoardGetRes boardGetRes = boardService.getBoard(boardId);
        if ("likes".equals(sortOrder)) {
            // 좋아요 순으로 정렬
            boardGetRes.getComments().sort(Comparator.comparing(CommentGetRes::getLikeCount).reversed());
        } else {
            // 최신순으로 정렬
            Collections.reverse(boardGetRes.getComments());
        }
        model.addAttribute("board", boardGetRes);
        return "getBoardDetail";
    }

    // 지역 게시글 조회 페이지
    @GetMapping("/region")
    public String getBoardsByRegion(
            @RequestParam RegionEnum region,
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAsc", defaultValue = DEFAULT_IS_ASC) boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        Page<BoardToListGetRes> responseDto =
                boardService.getBoardList(region, page - 1, size, sortBy, isAsc);
        model.addAttribute("region", region);
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", responseDto.getTotalPages());
        return "regionPage";
    }

    // 전체 게시글 조회 페이지
    @GetMapping("/all")
    public String getAllBoards(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAsc", defaultValue = DEFAULT_IS_ASC) boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());
        }
        Page<BoardToListGetRes> responseDto =
                boardService.getBoardAllList(page - 1, size, sortBy, isAsc);
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", responseDto.getTotalPages());
        return "allBoardPage";
    }

    // 사용자 게시글 조회 페이지
    @GetMapping("/user")
    public String getUserBoardList(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAsc", defaultValue = DEFAULT_IS_ASC) boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        model.addAttribute("user", userDetails.getUser());

        // BoardService의 getBoardUserList 메서드를 호출하여 사용자의 보드 목록을 가져옵니다.
        Page<BoardToReportGetRes> responseDto =
                boardService.getBoardUserList(userId, page - 1, size, sortBy, isAsc);

        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", responseDto.getTotalPages());
        return "userBoardPage";
    }

    // Best 게시글 조회 페이지
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
        return CommonResponse.success(
                boardService.saveBoard(boardSaveReq, boardSaveReq.getPhotoImages()));
    }

    // 지도 파일 저장
    @ResponseBody
    @PostMapping("/map")
    public String saveMapImage(MultipartFile mapImage) {
        return boardService.saveMapImage(mapImage); // s3에 저장한 이미지의 url을 반환
    }

    // 지도 파일 삭제
    @ResponseBody
    @DeleteMapping("/map")
    public void deleteMapImage(@RequestParam("mapImage") String mapImage) {
        boardService.deleteMapImage(mapImage);
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
                boardService.updateBoard(boardUpdateReq, boardUpdateReq.getPhotoImages()));
    }

    // 게시글 수정 페이지
    @GetMapping("/update/{boardId}")
    public String updateBoardDetails(
            @PathVariable Long boardId,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardGetRes boardGetRes = boardService.getBoard(boardId);
        model.addAttribute("board", boardGetRes);
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
