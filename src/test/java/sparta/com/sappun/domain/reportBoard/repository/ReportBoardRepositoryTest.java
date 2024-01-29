package sparta.com.sappun.domain.reportBoard.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.user.entity.User;
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
    @DisplayName("게시글 신고 save 테스트")
    void saveTest() {
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

    @Test
    @DisplayName("게시글 신고 existsReportBoardByBoardAndUser 테스트")
    void existsReportBoardByBoardAndUserTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        reportBoardRepository.save(REPORT_BOARD);

        // when
        boolean isPresent =
                reportBoardRepository.existsReportBoardByBoardAndUser(TEST_BOARD, TEST_USER);

        // then
        assertTrue(isPresent);
    }

    @Test
    @DisplayName("게시글 신고 selectUserByBoard 테스트")
    void selectUserByBoardTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        reportBoardRepository.save(REPORT_BOARD);

        // when
        List<User> reporters = reportBoardRepository.selectUserByBoard(TEST_BOARD);

        // then
        assertEquals(1, reporters.size());
    }

    @Test
    @DisplayName("게시글 신고 selectReportBoardByUser 테스트")
    void selectReportBoardByUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        reportBoardRepository.save(REPORT_BOARD);

        // when
        List<ReportBoard> reportBoardList = reportBoardRepository.selectReportBoardByUser(user);

        // then
        assertEquals(reportBoardList.get(0).getBoard(), board);
    }

    @Test
    @DisplayName("게시글 신고 deleteAll 테스트")
    public void deleteAllTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        ReportBoard reportBoard = reportBoardRepository.save(REPORT_BOARD);

        // when
        reportBoardRepository.deleteAll(List.of(reportBoard));
        boolean isExist = reportBoardRepository.existsReportBoardByBoardAndUser(board, user);

        // then
        assertFalse(isExist);
    }

    @Test
    @DisplayName("게시글 신고 clearReportBoardByBoard 테스트")
    void clearReportBoardByBoardTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        reportBoardRepository.save(REPORT_BOARD);

        // when
        reportBoardRepository.clearReportBoardByBoard(TEST_BOARD);

        // then
        assertEquals(0, reportBoardRepository.selectUserByBoard(TEST_BOARD).size());
    }

    @Test
    @DisplayName("게시글 신고 findAllFetchBoard 테스트")
    void findAllFetchBoardTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        ReportBoard reportBoard = reportBoardRepository.save(REPORT_BOARD);
        Pageable pageable = PageRequest.ofSize(1);

        // when
        Page<ReportBoard> reportBoardList = reportBoardRepository.findAllFetchBoard(pageable);

        // then
        assertEquals(reportBoard, reportBoardList.getContent().get(0));
    }
}
