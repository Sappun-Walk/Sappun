package sparta.com.sappun.domain.reportComment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.reportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.reportComment.repository.ReportCommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
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
        ReportCommentReq req = ReportCommentReq.builder().reason(TEST_COMMENT_REPORT_REASON).build();

        req.setUserId(TEST_USER_ID);

        // given
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
    }
}
