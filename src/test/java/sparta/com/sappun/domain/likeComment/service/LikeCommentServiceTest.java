package sparta.com.sappun.domain.likeComment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.likeComment.repository.LikeCommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.CommentTest;

@ExtendWith(MockitoExtension.class)
class LikeCommentServiceTest implements CommentTest {
    @Mock CommentRepository commentRepository;
    @Mock UserRepository userRepository;
    @Mock LikeCommentRepository likeCommentRepository;
    @InjectMocks LikeCommentService likecommentService;

    @Test
    @DisplayName("댓글 좋아요 저장 테스트 - 누르지 않은 상태인 경우")
    void clickLikeCommentTest() {
        // given
        Integer score = TEST_USER.getScore();
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        when(likeCommentRepository.existsLikeCommentByCommentAndUser(any(), any())).thenReturn(false);

        // when
        likecommentService.clickLikeComment(TEST_COMMENT_ID, TEST_USER_ID);

        // then
        assertEquals(score + 10, TEST_USER.getScore());
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(commentRepository, times(1)).findById(TEST_COMMENT_ID);
        verify(likeCommentRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("댓글 좋아요 저장 테스트 - 다시 누른 경우")
    void againClickLikeCommentTest() {
        // given
        Integer score = TEST_USER.getScore();
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        when(likeCommentRepository.existsLikeCommentByCommentAndUser(any(), any())).thenReturn(true);

        // when
        likecommentService.clickLikeComment(TEST_COMMENT_ID, TEST_USER_ID);

        // then
        assertEquals(score - 10, TEST_USER.getScore());
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(commentRepository, times(1)).findById(TEST_COMMENT_ID);
        verify(likeCommentRepository, times(1)).deleteLikeCommentByCommentAndUser(any(), any());
    }
}
