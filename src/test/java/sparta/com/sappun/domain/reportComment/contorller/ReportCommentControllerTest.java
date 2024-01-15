package sparta.com.sappun.domain.reportComment.contorller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.reportComment.controller.ReportCommentController;
import sparta.com.sappun.domain.reportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.reportComment.dto.response.DeleteReportCommentRes;
import sparta.com.sappun.domain.reportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.reportComment.service.ReportCommentService;
import sparta.com.sappun.test.ReportCommentTest;

@WebMvcTest(controllers = {ReportCommentController.class})
class ReportCommentControllerTest extends BaseMvcTest implements ReportCommentTest {
    @MockBean private ReportCommentService reportCommentService;

    @Test
    @DisplayName("댓글 신고 API 테스트")
    void reportCommentTest() throws Exception {
        // given
        ReportCommentReq req = ReportCommentReq.builder().reason(TEST_COMMENT_REPORT_REASON).build();
        req.setUserId(TEST_USER_ID);

        ReportCommentRes res =
                ReportCommentRes.builder()
                        .reporterUserId(TEST_USER_ID)
                        .reportCommentId(TEST_COMMENT_ID)
                        .reason(TEST_COMMENT_REPORT_REASON)
                        .build();

        when(reportCommentService.clickReportComment(any(), any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(
                        post("/api/comments/{commentId}/report", TEST_COMMENT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 신고 삭제 API 테스트")
    void deleteReportedCommentTest() throws Exception {
        // given
        DeleteReportCommentRes res = new DeleteReportCommentRes();
        when(reportCommentService.deleteReportComment(any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(delete("/api/comments/{commentId}/report", TEST_COMMENT_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
