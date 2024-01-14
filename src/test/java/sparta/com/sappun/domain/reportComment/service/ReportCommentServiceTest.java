package sparta.com.sappun.domain.reportComment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static sparta.com.sappun.global.response.ResultCode.DUPLICATED_REPORT_COMMENT;
import static sparta.com.sappun.test.BoardTest.TEST_BOARD;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.reportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.reportComment.repository.ReportCommentRepository;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.exception.GlobalException;
import sparta.com.sappun.test.ReportCommentTest;

@ExtendWith(MockitoExtension.class)
class ReportCommentServiceTest implements ReportCommentTest {

    @Mock private CommentRepository commentRepository;

    @Mock private UserRepository userRepository;

    @Mock private ReportCommentRepository reportCommentRepository;

    @InjectMocks private ReportCommentService reportCommentService;

    @Test
    @DisplayName("댓글 신고 테스트")
    void reportComment() {
        // given
        ReportCommentReq req = ReportCommentReq.builder().reason(TEST_COMMENT_REPORT_REASON).build();
        req.setUserId(TEST_USER_ID);
        Integer score = TEST_COMMENT.getUser().getScore();

        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        when(reportCommentRepository.existsReportBoardByCommentAndUser(any(), any())).thenReturn(false);

        // when
        reportCommentService.clickReportComment(TEST_COMMENT_ID, req);

        // then
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(commentRepository, times(1)).findById(TEST_COMMENT_ID);
        verify(reportCommentRepository, times(1)).existsReportBoardByCommentAndUser(any(), any());
        verify(reportCommentRepository, times(1)).save(any());
        assertEquals(score - 50, TEST_COMMENT.getUser().getScore());
    }

    @Test
    @DisplayName("댓글 신고 테스트 - 이미 신고한 경우")
    void duplicatedClickReportCommentTest() {
        // given
        ReportCommentReq req = ReportCommentReq.builder().reason(TEST_COMMENT_REPORT_REASON).build();
        req.setUserId(TEST_USER_ID);

        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        when(reportCommentRepository.existsReportBoardByCommentAndUser(any(), any())).thenReturn(true);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            reportCommentService.clickReportComment(TEST_COMMENT_ID, req);
                        });

        // then
        assertEquals(DUPLICATED_REPORT_COMMENT.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("댓글 신고 삭제 테스트")
    void deleteReportCommentTest() {
        // given
        User user1 =
                User.builder()
                        .username("username1")
                        .nickname("nickname1")
                        .password("password1")
                        .email("email1@dd.d")
                        .role(Role.USER)
                        .score(0)
                        .build();

        User user2 =
                User.builder()
                        .username("username2")
                        .nickname("nickname2")
                        .password("password2")
                        .email("email2@dd.d")
                        .role(Role.USER)
                        .score(0)
                        .build();

        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        Integer commentAuthorScore = TEST_COMMENT.getUser().getScore();
        List<User> reporters = List.of(user1, user2);
        when(reportCommentRepository.selectUserByComment(any())).thenReturn(reporters);

        // when
        reportCommentService.deleteReportComment(TEST_COMMENT_ID);

        // then
        verify(reportCommentRepository).clearReportCommentByComment(TEST_COMMENT);
        assertEquals(-50, user1.getScore());
        assertEquals(commentAuthorScore + 100, TEST_BOARD.getUser().getScore());
    }
}
