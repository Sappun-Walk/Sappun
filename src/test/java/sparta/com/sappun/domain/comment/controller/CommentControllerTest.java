package sparta.com.sappun.domain.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.service.CommentService;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.test.CommentTest;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest extends BaseMvcTest implements CommentTest {

    @MockBean private CommentService commentService;

    @MockBean private UserService userService;
    @InjectMocks private CommentController commentController;

    @Test
    @DisplayName("댓글 저장 API 테스트")
    void saveCommentTest() throws Exception {
        // given
        CommentSaveReq req =
                CommentSaveReq.builder()
                        .nickname(TEST_USER_NICKNAME)
                        .content(TEST_COMMENT_CONTENT)
                        .fileUrl(TEST_COMMENT_FILEURL)
                        .build();

        CommentSaveRes res =
                CommentSaveRes.builder()
                        .id(TEST_COMMENT_ID)
                        .nickname(TEST_USER_NICKNAME)
                        .content(TEST_COMMENT_CONTENT)
                        .fileUrl(TEST_COMMENT_FILEURL)
                        .build();
        when(commentService.saveComment(any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(
                        post("/api/comments")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }
}
