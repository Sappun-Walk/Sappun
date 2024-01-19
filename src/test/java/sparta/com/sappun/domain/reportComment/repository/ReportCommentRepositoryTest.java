package sparta.com.sappun.domain.reportComment.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.ReportCommentTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReportCommentRepositoryTest implements ReportCommentTest {

    @Autowired private ReportCommentRepository reportCommentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private BoardRepository boardRepository;

    @Test
    @DisplayName("댓글 신고 save 테스트")
    void saveTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);

        // when
        ReportComment reportComment = reportCommentRepository.save(REPORT_COMMENT);

        // then
        assertEquals(TEST_USER, reportComment.getUser());
        assertEquals(TEST_COMMENT, reportComment.getComment());
        assertEquals(TEST_COMMENT_REPORT_REASON, reportComment.getReason());
    }

    @Test
    @DisplayName("댓글 신고 existsReportBoardByBoardAndUser 테스트")
    void existsReportCommentByCommentAndUserTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);
        reportCommentRepository.save(REPORT_COMMENT);

        // when
        boolean isPresent =
                reportCommentRepository.existsReportCommentByCommentAndUser(TEST_COMMENT, TEST_USER);

        // then
        assertTrue(isPresent);
    }

    @Test
    @DisplayName("댓글 신고 selectUserByComment 테스트")
    void selectUserByCommentTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);
        reportCommentRepository.save(REPORT_COMMENT);

        // when
        List<User> reporters = reportCommentRepository.selectUserByComment(TEST_COMMENT);

        // then
        assertEquals(1, reporters.size());
    }

    @Test
    @DisplayName("댓글 신고 clearReportCommentByComment 테스트")
    void clearReportCommentByCommentTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);
        reportCommentRepository.save(REPORT_COMMENT);

        // when
        reportCommentRepository.clearReportCommentByComment(TEST_COMMENT);

        // then
        assertEquals(0, reportCommentRepository.selectUserByComment(TEST_COMMENT).size());
    }

    @Test
    @DisplayName("댓글 신고 findAllFetchComment 테스트")
    void findAllFetchComment() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);
        reportCommentRepository.save(REPORT_COMMENT);

        // when
        List<ReportComment> result = reportCommentRepository.findAllFetchComment();

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("댓글 신고 selectReportCommentByUser 테스트")
    void selectReportCommentByUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);
        ReportComment reportComment = reportCommentRepository.save(REPORT_COMMENT);

        // when
        List<ReportComment> reportCommentList = reportCommentRepository.selectReportCommentByUser(user);

        // then
        assertEquals(reportCommentList.get(0), reportComment);
    }

    @Test
    @DisplayName("댓글 신고 deleteAll 테스트")
    public void deleteAllTest() {
        // given
        User user = userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);
        ReportComment reportComment = reportCommentRepository.save(REPORT_COMMENT);

        // when
        reportCommentRepository.deleteAll(List.of(reportComment));
        boolean isExist = reportCommentRepository.existsReportCommentByCommentAndUser(comment, user);

        // then
        assertFalse(isExist);
    }
}
