package sparta.com.sappun.domain.board.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.test.BoardTest;

@Disabled
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest implements BoardTest {
    @Autowired private BoardRepository boardRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given

        // when
        Board saveBoard = boardRepository.save(TEST_BOARD);

        // then
        assertEquals(TEST_BOARD_ID, saveBoard.getId());
        assertEquals(TEST_BOARD_TITLE, saveBoard.getTitle());
        assertEquals(TEST_BOARD_CONTENT, saveBoard.getContent());
        assertEquals(TEST_BOARD_URL, saveBoard.getFileURL());
        assertEquals(TEST_DEPARTURE, saveBoard.getDeparture());
        assertEquals(TEST_DESTINATION, saveBoard.getDestination());
        assertEquals(TEST_STOPOVER, saveBoard.getStopover());
        assertEquals(TEST_REGION1, saveBoard.getRegion());
        assertEquals(TEST_LIKECOUNT, saveBoard.getLikeCount());
        assertEquals(TEST_REPORTCOUNT, saveBoard.getReportCount());
    }

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        Board board = boardRepository.save(TEST_BOARD);
        // when
        Board saveBoard = boardRepository.findById(board.getId());

        // then
        assertEquals(TEST_BOARD_ID, saveBoard.getId());
        assertEquals(TEST_BOARD_TITLE, saveBoard.getTitle());
        assertEquals(TEST_BOARD_CONTENT, saveBoard.getContent());
        assertEquals(TEST_BOARD_URL, saveBoard.getFileURL());
        assertEquals(TEST_DEPARTURE, saveBoard.getDeparture());
        assertEquals(TEST_DESTINATION, saveBoard.getDestination());
        assertEquals(TEST_STOPOVER, saveBoard.getStopover());
        assertEquals(TEST_REGION1, saveBoard.getRegion());
        assertEquals(TEST_LIKECOUNT, saveBoard.getLikeCount());
        assertEquals(TEST_REPORTCOUNT, saveBoard.getReportCount());
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        Board board = boardRepository.save(TEST_BOARD);
        // when
        boardRepository.delete(board);
        Board findBoard = boardRepository.findById(board.getId());

        // then
        assertNull(findBoard);
    }

    @Test
    @DisplayName("findAllByRegion 테스트")
    void findAllByRegionTest() {
        //        // given
        //        Board board = boardRepository.save(TEST_BOARD);
        //        Pageable pageable = (Pageable) PageRequest.ofSize (1);
        //
        //        // when
        //        Page<Board> boardPage = boardRepository.findAllByRegion(board.getRegion(), pageable);
        //
        //        // then
        //        assertEquals(TEST_BOARD_ID, boardPage.getId());
        //        assertEquals(TEST_BOARD_TITLE, boardPage.getTitle());
        //        assertEquals(TEST_BOARD_CONTENT, boardPage.getContent());
        //        assertEquals(TEST_BOARD_URL, boardPage.getFileURL());
        //        assertEquals(TEST_DEPARTURE, boardPage.getDeparture());
        //        assertEquals(TEST_DESTINATION, boardPage.getDestination());
        //        assertEquals(TEST_STOPOVER, boardPage.getStopover());
        //        assertEquals(TEST_REGION1, boardPage.getRegion());
        //        assertEquals(TEST_LIKECOUNT, boardPage.getLikeCount());
        //        assertEquals(TEST_REPORTCOUNT, boardPage.getReportCount());
    }

    @Test
    @DisplayName("findTop3ByOrderByLikeCountDesc 테스트")
    void findTop3ByOrderByLikeCountDesc() {
        // given
        Board board1 = boardRepository.save(TEST_BOARD);
        Board board2 = boardRepository.save(TEST_BOARD1);
        Board board3 = boardRepository.save(TEST_BOARD2);

        // when
        List<Board> top3Boards = boardRepository.findTop3ByOrderByLikeCountDesc();

        // then
        assertEquals(3, top3Boards.size());
        assertEquals(TEST_BOARD2.getLikeCount(), top3Boards.get(0).getLikeCount());
        assertEquals(TEST_BOARD1.getLikeCount(), top3Boards.get(1).getLikeCount());
        assertEquals(TEST_BOARD.getLikeCount(), top3Boards.get(2).getLikeCount());
    }
}
