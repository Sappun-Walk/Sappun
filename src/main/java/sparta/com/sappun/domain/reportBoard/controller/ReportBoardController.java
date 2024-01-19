package sparta.com.sappun.domain.reportBoard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.reportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.reportBoard.dto.response.DeleteReportBoardRes;
import sparta.com.sappun.domain.reportBoard.dto.response.ReportBoardListGetRes;
import sparta.com.sappun.domain.reportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.reportBoard.service.ReportBoardService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ReportBoardController {

    @Autowired private ReportBoardService reportBoardService;

    @PostMapping("/{boardId}/report")
    public CommonResponse<ReportBoardRes> reportBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid ReportBoardReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(reportBoardService.clickReportBoard(boardId, req));
    }

    @DeleteMapping("/{boardId}/report") // 필터에서 관리자만 접근하도록 막기
    public CommonResponse<DeleteReportBoardRes> deleteReportedBoard(@PathVariable Long boardId) {
        return CommonResponse.success(reportBoardService.deleteReportBoard(boardId));
    }

    // 신고된 게시글 조회
    @GetMapping("/reports") // 필터에서 관리자만 접근하도록 막기
    public CommonResponse<ReportBoardListGetRes> getReportBoardList() {
        return CommonResponse.success(reportBoardService.getReportBoardList());
    }
}
