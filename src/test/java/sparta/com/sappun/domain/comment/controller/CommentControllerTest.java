package sparta.com.sappun.domain.comment.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.request.CommentUpdateReq;
import sparta.com.sappun.domain.comment.dto.response.CommentDeleteRes;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.dto.response.CommentUpdateRes;
import sparta.com.sappun.domain.comment.service.CommentService;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.test.CommentTest;

@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest extends BaseMvcTest implements CommentTest {

    @MockBean private CommentService commentService;

    @MockBean private UserService userService;

    static MockMultipartFile multipartFile;

    @BeforeAll
    static void setUpFileUrl() throws IOException {
        String imageUrl = "static/images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @Test
    @DisplayName("댓글 저장 API 테스트")
    void saveCommentTest() throws Exception {
        // given
        CommentSaveReq commentSaveReq = CommentSaveReq.builder().content(TEST_COMMENT_CONTENT).build();

        MockMultipartFile req =
                new MockMultipartFile(
                        "data",
                        null,
                        "application/json",
                        objectMapper.writeValueAsString(commentSaveReq).getBytes(StandardCharsets.UTF_8));

        CommentSaveRes res = CommentSaveRes.builder().id(TEST_COMMENT_ID).build();

        when(commentService.saveComment(any(), any())).thenReturn(res);

        // when-then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(
                                        HttpMethod.POST, "/api/{boardId}/comments", TEST_BOARD_ID)
                                .file(multipartFile)
                                .file(req)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .principal(mockPrincipal)) // 실제 사용자 정보 제공 필요
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 수정 api 테스트")
    void updateCommentTest() throws Exception {
        // given
        CommentUpdateReq commentUpdateReq =
                CommentUpdateReq.builder().content(TEST_COMMENT_UPDATE_CONTENT).build();

        MockMultipartFile req =
                new MockMultipartFile(
                        "data",
                        null,
                        "application/json",
                        objectMapper.writeValueAsString(commentUpdateReq).getBytes(StandardCharsets.UTF_8));

        CommentUpdateRes res =
                CommentUpdateRes.builder()
                        .id(TEST_COMMENT_ID)
                        .nickname(TEST_USER_NICKNAME)
                        .content(TEST_COMMENT_UPDATE_CONTENT)
                        .fileURL(TEST_COMMENT_FILEURL)
                        .build();
        when(commentService.updateComment(any(), any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(
                                        HttpMethod.PATCH, "/api/comments/{commentId}", TEST_COMMENT_ID)
                                .file(multipartFile)
                                .file(req)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("댓글 삭제 Api 테스트")
    void deleteCommentTest() throws Exception {
        // given
        CommentDeleteRes res = new CommentDeleteRes();
        when(commentService.deleteComment(anyLong(), anyLong())).thenReturn(res);

        // when - then
        mockMvc
                .perform(delete("/api/comments/{commentId}", TEST_COMMENT_ID).principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
