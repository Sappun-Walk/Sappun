package sparta.com.sappun.domain.ReportComment.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.ReportCommentTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReportCommentRepositoryTest implements ReportCommentTest {

    @Autowired private ReportCommentRepository reportCommentRepository;
    @Autowired private UserRepository userRepository;

    @Autowired private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 신고 레포 테스트")
    void save() {
        // given
        userRepository.save(TEST_USER);
        commentRepository.save(TEST_COMMENT);
        reportCommentRepository.save(REPORT_COMMENT);

        // when
        ReportComment reportComment = reportCommentRepository.save(REPORT_COMMENT);

        // then
        assertEquals(TEST_USER, reportComment.getUser());
        assertEquals(TEST_COMMENT, reportComment.getComment());
        assertEquals(TEST_COMMENT_REASON, reportComment.getReason());
    }
}
