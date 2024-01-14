package sparta.com.sappun.domain.ReportBoard.contorller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.ReportBoard.dto.request.ReportBoardReq;
import sparta.com.sappun.domain.ReportBoard.dto.response.ReportBoardRes;
import sparta.com.sappun.domain.ReportBoard.service.ReportBoardService;
import sparta.com.sappun.test.ReportBoardTest;

@WebMvcTest(controllers = {ReportBoardController.class})
class ReportBoardControllerTest extends BaseMvcTest implements ReportBoardTest {
    @MockBean private ReportBoardService reportBoardService;

    @Test
    @DisplayName("게시글 신고 API 테스트")
    void reportBoardTest() throws Exception {
        // given
        ReportBoardReq req = ReportBoardReq.builder().reason(TEST_BOARD_REPORT_REASON).build();

        ReportBoardRes res =
                ReportBoardRes.builder()
                        .reporterUserId(TEST_USER_ID)
                        .reportedBoardId(TEST_BOARD_ID)
                        .reason(TEST_BOARD_REPORT_REASON)
                        .build();

        when(reportBoardService.clickReportBoard(any(), any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(
                        post("/api/boards/{boardId}/report", TEST_BOARD_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req))
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }
}
