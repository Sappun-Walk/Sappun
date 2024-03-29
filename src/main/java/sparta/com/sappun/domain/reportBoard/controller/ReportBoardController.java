package sparta.com.sappun.domain.reportBoard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.reportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.reportBoard.dto.response.DeleteReportBoardRes;
import sparta.com.sappun.domain.reportBoard.dto.response.ReportBoardGetRes;
import sparta.com.sappun.domain.reportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.reportBoard.service.ReportBoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ReportBoardController {

    @Autowired private ReportBoardService reportBoardService;

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SIZE = "8";
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final String DEFAULT_IS_ASC = "false";

    @ResponseBody
    @PostMapping("/{boardId}/report")
    public CommonResponse<ReportBoardRes> reportBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid ReportBoardReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(reportBoardService.clickReportBoard(boardId, req));
    }

    @ResponseBody
    @DeleteMapping("/{boardId}/report") // 필터에서 관리자만 접근하도록 막기
    public CommonResponse<DeleteReportBoardRes> deleteReportedBoard(@PathVariable Long boardId) {
        return CommonResponse.success(reportBoardService.deleteReportBoard(boardId));
    }

    // 신고된 게시글 조회
    @GetMapping("/reports") // 필터에서 관리자만 접근하도록 막기
    public String getReportBoardList(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAsc", defaultValue = DEFAULT_IS_ASC) boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        Page<ReportBoardGetRes> responseDto =
                reportBoardService.getReportBoardList(page - 1, size, sortBy, isAsc);
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", responseDto.getTotalPages());
        return "reportBoardPage";
    }
}
