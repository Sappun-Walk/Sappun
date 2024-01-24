package sparta.com.sappun.domain.reportComment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.reportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.reportComment.dto.response.DeleteReportCommentRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentGetRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.reportComment.service.ReportCommentService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Controller
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class ReportCommentController {
    @Autowired private ReportCommentService reportCommentService;

    private static final String DEFAULT_PAGE = "1";
    private static final Integer MAX_PAGE = 5;
    private static final String DEFAULT_SIZE = "8";
    private static final String DEFAULT_SORT_BY = "createdAt";
    private static final String DEFAULT_IS_ASC = "false";

    @ResponseBody
    @PostMapping("/{commentId}/report")
    public CommonResponse<ReportCommentRes> reportComment(
            @PathVariable Long commentId,
            @RequestBody @Valid ReportCommentReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getId());
        return CommonResponse.success(reportCommentService.clickReportComment(commentId, req));
    }

    @ResponseBody
    @DeleteMapping("/{commentId}/report") // 필터에서 관리자만 접근하도록 막기
    public CommonResponse<DeleteReportCommentRes> deleteReportedComment(
            @PathVariable Long commentId) {
        return CommonResponse.success(reportCommentService.deleteReportComment(commentId));
    }

    @GetMapping("/reports") // 필터에서 관리자만 접근하도록 막기
    public String getReportCommentList(
            @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
            @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(value = "isAsc", defaultValue = DEFAULT_IS_ASC) boolean isAsc,
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        Page<ReportCommentGetRes> responseDto =
                reportCommentService.getReportCommentList(page - 1, size, sortBy, isAsc);
        model.addAttribute("responseDto", responseDto);
        model.addAttribute("maxPage", MAX_PAGE);
        return "reportCommentPage";
    }
}
