package sparta.com.sappun.domain.comment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.CommentTest;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest implements CommentTest {

    @Mock CommentRepository commentRepository;
    @Mock UserRepository userRepository;
    @InjectMocks CommentService commentService;
    @Captor ArgumentCaptor<Comment> argumentCaptor;

    @Test
    @DisplayName("댓글 저장 테스트")
    void saveCommentTest() {
        // given
        CommentSaveReq req =
                CommentSaveReq.builder()
                        .content(TEST_COMMENT_CONTENT)
                        .fileUrl(TEST_COMMENT_FILEURL)
                        .build();
        req.setUserId(TEST_USER_ID);
        when(userRepository.findById(any())).thenReturn(TEST_USER);

        // when
        commentService.saveComment(req);

        // then
        verify(commentRepository).save(argumentCaptor.capture());
        assertEquals(TEST_COMMENT_CONTENT, argumentCaptor.getValue().getContent());
        assertEquals(TEST_COMMENT_FILEURL, argumentCaptor.getValue().getFileUrl());
    }

    //        @Test
    //        @DisplayName("댓글 저장 실패 테스트 - 사용자 권한 없음")
    //        void invalidSaveCommentTest() {
    //            // given
    //            CommentSaveReq req =
    //                    CommentSaveReq.builder()
    //                            .nickname(null)
    //                            .content(TEST_COMMENT_CONTENT)
    //                            .fileUrl(TEST_COMMENT_FILEURL)
    //                            .build();
    //
    //            // when
    //            GlobalException exception =
    //                    assertThrows(
    //                            GlobalException.class,
    //                            () -> {
    //                                commentService.saveComment(req);
    //                            });
    //
    //            // then
    //            assertEquals(NOT_FOUND_BOARD.getMessage(), exception.getResultCode().getMessage());
    //        }
}
