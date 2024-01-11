package sparta.com.sappun.domain.ReportComment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sparta.com.sappun.domain.ReportComment.repository.ReportCommentRepository;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.ReportCommentTest;

@SpringBootTest
@Transactional
class ReportCommentServiceTest implements ReportCommentTest {

    @MockBean private CommentRepository commentRepository;

    @MockBean private UserRepository userRepository;

    @MockBean private ReportCommentRepository reportCommentRepository;

    @Autowired private ReportCommentService reportCommentService;

    @Test
    @DisplayName("댓글 신고 테스트")
    public void reportCommentRes() {
        // given
        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(commentRepository.findById(any())).thenReturn(TEST_COMMENT);
        // when
        reportCommentService.reportCommentRes(TEST_COMMENT_ID, TEST_REPORT_COMMENT_REQ);
        // then
        verify(userRepository, times(1)).findById(TEST_USER_ID);
        verify(commentRepository, times(1)).findById(TEST_COMMENT_ID);
        verify(reportCommentRepository, times(1)).save(any());
    }
}
