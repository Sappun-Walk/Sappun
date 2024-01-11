package sparta.com.sappun.domain.comment.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.comment.service.CommentService;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.test.CommentTest;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest extends BaseMvcTest implements CommentTest {

    @MockBean private CommentService commentService;

    @MockBean private UserService userService;

    // TODO: Board TEST 작성후 다시 TEST
    //    @Test
    //    @DisplayName("댓글 저장 API 테스트")
    //    void saveCommentTest() throws Exception {
    //        // given
    //        CommentSaveReq req =
    //                CommentSaveReq.builder()
    //                        .content(TEST_COMMENT_CONTENT)
    //                        .fileUrl(TEST_COMMENT_FILEURL)
    //                        .build();
    //
    //        CommentSaveRes res =
    //                CommentSaveRes.builder()
    //                        .id(TEST_COMMENT_ID)
    //                        .nickname(TEST_USER_NICKNAME)
    //                        .content(TEST_COMMENT_CONTENT)
    //                        .fileUrl(TEST_COMMENT_FILEURL)
    //                        .build();
    //        when(commentService.saveComment(any())).thenReturn(res);
    //
    //        // when-then
    //        mockMvc
    //                .perform(
    //                        post("/api/{boardId}/comments")
    //                                .content(objectMapper.writeValueAsString(req))
    //                                .contentType(MediaType.APPLICATION_JSON)
    //                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
    //                .andDo(print())
    //                .andExpect(status().isOk());
    //    }

    //    @Test
    //    @DisplayName("댓글 수정 api 테스트")
    //    void updateCommentTest() throws Exception {
    //        // given
    //        CommentUpdateReq req =
    //                CommentUpdateReq.builder()
    //                        .content(TEST_COMMENT_CONTENT)
    //                        .fileUrl(TEST_COMMENT_FILEURL)
    //                        .build();
    //        CommentUpdateRes res =
    //                CommentUpdateRes.builder()
    //                        .id(TEST_COMMENT_ID)
    //                        .nickname(TEST_USER_NICKNAME)
    //                        .content(TEST_COMMENT_CONTENT)
    //                        .fileUrl(TEST_COMMENT_FILEURL)
    //                        .build();
    //        when(commentService.updateComment(any())).thenReturn(res);
    //
    //        // when - then
    //        mockMvc
    //                .perform(
    //                        patch("/api/comments/{commentId}")
    //                                .content(objectMapper.writeValueAsString(req))
    //                                .contentType(MediaType.APPLICATION_JSON)
    //                                .principal(mockPrincipal))
    //                .andDo(print())
    //                .andExpect(status().isOk());
    //    }

}
