package sparta.com.sappun.domain.ReportBoard.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.ReportBoardTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReportBoardRepositoryTest implements ReportBoardTest {
    @Autowired private ReportBoardRepository reportBoardRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 신고 레포 테스트")
    void save() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);

        // when
        ReportBoard reportBoard = reportBoardRepository.save(REPORT_BOARD);

        // then
        assertEquals(TEST_USER, reportBoard.getUser());
        assertEquals(TEST_BOARD, reportBoard.getBoard());
        assertEquals(TEST_BOARD_REPORT_REASON, reportBoard.getReason());
    }
}
