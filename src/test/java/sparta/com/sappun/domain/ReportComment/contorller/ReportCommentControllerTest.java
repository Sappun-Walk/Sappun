package sparta.com.sappun.domain.ReportComment.contorller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.LikeComment.contorller.LikeCommentLikeController;
import sparta.com.sappun.domain.LikeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.LikeComment.service.LikeCommentService;
import sparta.com.sappun.domain.ReportComment.controller.ReportCommentController;
import sparta.com.sappun.domain.ReportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.ReportComment.dto.response.ReportCommentRes;
import sparta.com.sappun.domain.ReportComment.service.ReportCommentService;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.global.security.UserDetailsImpl;
import sparta.com.sappun.test.LikeCommentTest;
import sparta.com.sappun.test.ReportCommentTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ReportCommentController.class})
class ReportCommentControllerTest extends BaseMvcTest implements ReportCommentTest {
    @MockBean
    private ReportCommentService reportCommentService;

    @Test
    @DisplayName("댓글 신고 API 테스트")
    void reportCommentTest() throws Exception {
        // given
        ReportCommentReq req =
                ReportCommentReq.builder().reportCommentId(TEST_COMMENT_ID)
                        .reason(TEST_COMMENT_REASON)
                        .build();

        ReportCommentRes res =
                ReportCommentRes.builder().reporterUserId(TEST_USER_ID)
                                .reportCommentId(TEST_COMMENT_ID)
                                .reason(TEST_COMMENT_REASON)
                                .build();

        when(reportCommentService.reportCommentRes(any(), any())).thenReturn(res);

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
}
