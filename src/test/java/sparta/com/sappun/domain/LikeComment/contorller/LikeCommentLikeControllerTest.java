package sparta.com.sappun.domain.LikeComment.contorller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.LikeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.LikeComment.repository.LikeCommentRepository;
import sparta.com.sappun.domain.LikeComment.service.LikeCommentService;
import sparta.com.sappun.domain.comment.controller.CommentController;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.service.CommentService;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;
import sparta.com.sappun.test.CommentTest;
import sparta.com.sappun.test.LikeCommentTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {LikeCommentLikeController.class})
class LikeCommentLikeControllerTest extends BaseMvcTest implements LikeCommentTest {

    @MockBean
    private LikeCommentService likeCommentService;

    @Test
    @DisplayName("댓글 저장 API 테스트")
    void saveCommentTest() throws Exception {
        // given
        Long commentId = 1L;
        LikeCommentSaveRes res = new LikeCommentSaveRes();
        // when-then
        when(likeCommentService.likeCommentSaveRes(any(),any())).thenReturn(res);
        mockMvc
                .perform(
                        post("/api/comments/{commentId}/like",commentId)
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }
}