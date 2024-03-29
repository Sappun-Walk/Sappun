package sparta.com.sappun.domain.likeComment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.likeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.likeComment.service.LikeCommentService;
import sparta.com.sappun.test.LikeCommentTest;

@WebMvcTest(controllers = {LikeCommentController.class})
class LikeCommentControllerTest extends BaseMvcTest implements LikeCommentTest {

    @MockBean private LikeCommentService likeCommentService;

    @Test
    @DisplayName("댓글 좋아요 API 테스트")
    void likeCommentTest() throws Exception {
        // given
        LikeCommentSaveRes res = LikeCommentSaveRes.builder().isLiked(true).build();
        when(likeCommentService.clickLikeComment(any(), any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(
                        post("/api/comments/{commentId}/like", TEST_COMMENT_ID)
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }
}
