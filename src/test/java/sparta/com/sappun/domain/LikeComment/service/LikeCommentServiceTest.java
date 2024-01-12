package sparta.com.sappun.domain.LikeComment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.LikeComment.dto.response.LikeCommentSaveRes;
import sparta.com.sappun.domain.LikeComment.repository.LikeCommentRepository;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.CommentTest;

@ExtendWith(MockitoExtension.class)
class LikeCommentServiceTest implements CommentTest {
    @Mock CommentRepository commentRepository;
    @Mock UserRepository userRepository;
    @Mock LikeCommentRepository likeCommentRepository;
    @InjectMocks LikeCommentService likecommentService;
    @Captor ArgumentCaptor<Comment> argumentCaptor;

    @Test
    @DisplayName("댓글 좋아요 저장 테스트")
    void saveLikeCommentTest() {
        // given
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        // when
        LikeCommentSaveRes result = likecommentService.clickLikeComment(TEST_COMMENT_ID, TEST_USER_ID);

        // then
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(commentRepository, times(1)).findById(TEST_COMMENT_ID);
        verify(likeCommentRepository, times(1)).save(any());
    }
}
