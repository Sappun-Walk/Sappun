package sparta.com.sappun.domain.ReportBoard.contorller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.ReportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.service.ReportBoardService;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class ReportBoardController {

    @Autowired
    private ReportBoardService reportBoardService;

    @PostMapping("/{boardId}/report")
    public ResponseEntity<ReportBoardRes> reportBoard(@PathVariable Long boardId, @RequestBody ReportBoardReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            ReportBoardRes reportBoardRes = reportBoardService.reportBoardRes(boardId, req, userDetails.getUser().getId());
            return ResponseEntity.ok(reportBoardRes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // 적절한 에러 응답 처리
        }
    }
}