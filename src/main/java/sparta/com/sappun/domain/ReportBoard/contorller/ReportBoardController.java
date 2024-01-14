package sparta.com.sappun.domain.ReportBoard.contorller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.ReportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.ReportBoard.dto.response.DeleteReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.service.ReportBoardService;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ReportBoardController {

    @Autowired private ReportBoardService reportBoardService;

    @PostMapping("/{boardId}/report")
    public ResponseEntity<ReportBoardRes> reportBoard(
            @PathVariable Long boardId,
            @RequestBody @Valid ReportBoardReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setUserId(userDetails.getUser().getId());
        return ResponseEntity.ok(reportBoardService.clickReportBoard(boardId, req));
    }

    @DeleteMapping("/{boardId}/report") // 필터에서 관리자만 접근하도록 막기
    public ResponseEntity<DeleteReportBoardRes> deleteReportedBoard(@PathVariable Long boardId) {
        return ResponseEntity.ok(reportBoardService.deleteReportBoard(boardId));
    }
}
