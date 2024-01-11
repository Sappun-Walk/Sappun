package sparta.com.sappun.domain.comment.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    //TODO: Board TEST 작성후 다시 TEST
    //    @Test
    //    @DisplayName("댓글 저장 테스트")
    //    void saveCommentTest() {
    //        // given
    //        TEST_COMMENT_SAVE.setUserId(TEST_USER_ID);
    //        when(userRepository.findById(any())).thenReturn(TEST_USER);
    //
    //        // when
    //        commentService.saveComment(TEST_COMMENT_SAVE);
    //
    //        // then
    //        verify(commentRepository).save(argumentCaptor.capture());
    //        assertEquals(TEST_COMMENT_CONTENT, argumentCaptor.getValue().getContent());
    //        assertEquals(TEST_COMMENT_FILEURL, argumentCaptor.getValue().getFileUrl());
    //    }

    //            @Test
    //            @DisplayName("댓글 저장 실패 테스트 - 사용자 권한 없음")
    //            void invalidSaveCommentTest() {
    //                // given
    //
    //                // when
    //                GlobalException exception =
    //                        assertThrows(
    //                                GlobalException.class,
    //                                () -> {
    //                                    commentService.saveComment(TEST_COMMENT_SAVE);
    //                                });
    //
    //                // then
    //                assertEquals(NOT_FOUND_BOARD.getMessage(),
    // exception.getResultCode().getMessage());
    //            }
    //
    //        @Test
    //        @DisplayName("댓글 수정 테스트")
    //        void updateCommentTest() {
    //            // given
    //
    //
    // given(commentRepository.findById(TEST_COMMENT_UPDATE.getCommentId())).willReturn(TEST_COMMENT);
    //
    //            // when
    //            commentService.updateComment(TEST_COMMENT_UPDATE);
    //
    //            // then
    //            verify(commentRepository).save(argumentCaptor.capture());
    //            assertEquals(TEST_COMMENT_UPDATE_CONTENT, argumentCaptor.getValue().getContent());
    //        }
}
