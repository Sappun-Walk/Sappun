package sparta.com.sappun.domain.comment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.request.CommentUpdateReq;
import sparta.com.sappun.domain.comment.dto.response.CommentSaveRes;
import sparta.com.sappun.domain.comment.dto.response.CommentUpdateRes;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.infra.s3.S3Util;
import sparta.com.sappun.test.CommentTest;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest implements CommentTest {

    @Mock CommentRepository commentRepository;
    @Mock UserRepository userRepository;
    @Mock BoardRepository boardRepository;
    @Mock S3Util s3Util;
    @InjectMocks CommentService commentService;
    @Captor ArgumentCaptor<Comment> argumentCaptor;
    static MultipartFile multipartFile;

    @BeforeAll
    static void setUpProfile() throws IOException {
        String imageUrl = "static/images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @Test
    @DisplayName("댓글 저장 테스트")
    void saveCommentTest() {
        // given
        CommentSaveReq req = CommentSaveReq.builder().content(TEST_COMMENT_CONTENT).build();

        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_COMMENT_FILEURL);
        when(boardRepository.findById(any())).thenReturn(TEST_BOARD);
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.save(any())).thenReturn(TEST_COMMENT);

        // when
        CommentSaveRes result = commentService.saveComment(req, multipartFile);

        // then
        verify(commentRepository).save(argumentCaptor.capture());
        verify(s3Util, times(1)).uploadFile(any(), any());
        verify(userRepository, times(1)).findById(any());
        verify(boardRepository, times(1)).findById(any());
        verify(commentRepository, times(1)).save(any());
        assertEquals(TEST_USER_NICKNAME, argumentCaptor.getValue().getUser().getNickname());
        assertEquals(TEST_COMMENT_CONTENT, argumentCaptor.getValue().getContent());
        assertEquals(TEST_COMMENT_FILEURL, argumentCaptor.getValue().getFileURL());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateCommentTest() {
        // Given
        CommentUpdateReq req = CommentUpdateReq.builder().content(TEST_COMMENT_CONTENT).build();
        req.setCommentId(TEST_COMMENT_ID);
        req.setUserId(TEST_USER_ID);

        when(commentRepository.findById(req.getCommentId())).thenReturn(TEST_COMMENT);
        when(userRepository.findById(req.getUserId())).thenReturn(TEST_USER);
        ReflectionTestUtils.setField(TEST_USER, "id", TEST_USER_ID);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_COMMENT_FILEURL);

        // When
        CommentUpdateRes updateRes = commentService.updateComment(req, multipartFile);

        // Then
        assertEquals(TEST_COMMENT_CONTENT, updateRes.getContent());
        assertEquals(TEST_COMMENT_FILEURL, updateRes.getFileURL());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteCommentTest() {
        // given
        ReflectionTestUtils.setField(TEST_USER, "id", TEST_USER_ID);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        when(userRepository.findById(any())).thenReturn(TEST_USER);

        // when
        commentService.deleteComment(TEST_COMMENT_ID, TEST_USER_ID);

        // then
        verify(commentRepository).findById(any());
        verify(commentRepository).delete(any());
    }
}
