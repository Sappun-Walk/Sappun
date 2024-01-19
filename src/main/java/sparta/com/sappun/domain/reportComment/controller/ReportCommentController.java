package sparta.com.sappun.domain.reportComment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.reportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.reportComment.dto.response.DeleteReportCommentRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentListGetRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.reportComment.service.ReportCommentService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class ReportCommentController {
    @Autowired private ReportCommentService reportCommentService;

    @PostMapping("/{commentId}/report")
    public CommonResponse<ReportCommentRes> reportComment(
            @PathVariable Long commentId,
            @RequestBody @Valid ReportCommentReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(reportCommentService.clickReportComment(commentId, req));
    }

    @DeleteMapping("/{commentId}/report") // 필터에서 관리자만 접근하도록 막기
    public CommonResponse<DeleteReportCommentRes> deleteReportedComment(
            @PathVariable Long commentId) {
        return CommonResponse.success(reportCommentService.deleteReportComment(commentId));
    }

    @GetMapping("/reports") // 필터에서 관리자만 접근하도록 막기
    public CommonResponse<ReportCommentListGetRes> getReportCommentList() {
        return CommonResponse.success(reportCommentService.getReportCommentList());
    }
}
