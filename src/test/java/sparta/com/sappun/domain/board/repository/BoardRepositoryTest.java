package sparta.com.sappun.domain.board.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.BoardTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest implements BoardTest {

    @Autowired private BoardRepository boardRepository;
    @Autowired private UserRepository userRepository;

    @PersistenceContext EntityManager em;

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);

        // when
        Board saveBoard = boardRepository.findById(board.getId());

        // then
        assertEquals(TEST_BOARD_TITLE, saveBoard.getTitle());
        assertEquals(TEST_BOARD_CONTENT, saveBoard.getContent());
        assertEquals(TEST_STOPOVER, saveBoard.getStopover());
        assertEquals(TEST_REGION1, saveBoard.getRegion());
        assertEquals(TEST_LIKE_COUNT, saveBoard.getLikeCount());
        assertEquals(TEST_REPORT_COUNT, saveBoard.getReportCount());
    }

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        userRepository.save(TEST_USER);

        // when
        Board saveBoard = boardRepository.save(TEST_BOARD);

        // then
        assertEquals(TEST_BOARD_ID, saveBoard.getId());
        assertEquals(TEST_BOARD_TITLE, saveBoard.getTitle());
        assertEquals(TEST_BOARD_CONTENT, saveBoard.getContent());
        assertEquals(TEST_STOPOVER, saveBoard.getStopover());
        assertEquals(TEST_REGION1, saveBoard.getRegion());
        assertEquals(TEST_LIKE_COUNT, saveBoard.getLikeCount());
        assertEquals(TEST_REPORT_COUNT, saveBoard.getReportCount());
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        // when
        boardRepository.delete(board);
        Board findBoard = boardRepository.findById(board.getId());

        // then
        assertNull(findBoard);
    }

    @Test
    @DisplayName("deleteAllByUser 테스트")
    @Transactional
    void deleteAllByUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board1 =
                Board.builder()
                        .user(user)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .fileURL(TEST_MAP_IMAGE)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .likeCount(0)
                        .reportCount(TEST_REPORT_COUNT)
                        .build();
        boardRepository.save(board1);

        Board board2 =
                Board.builder()
                        .user(user)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .fileURL(TEST_MAP_IMAGE)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .likeCount(0)
                        .reportCount(TEST_REPORT_COUNT)
                        .build();
        boardRepository.save(board2);

        // when
        boardRepository.deleteAllByUser(user);
        em.flush(); // 변경사항을 데이터베이스에 즉시 반영
        em.clear(); // 영속성 컨텍스트 초기화
        Board findBoard1 = boardRepository.findById(board1.getId());
        Board findBoard2 = boardRepository.findById(board2.getId());

        // then
        assertNull(findBoard1);
        assertNull(findBoard2);
    }

    @Test
    @DisplayName("findTop3ByOrderByLikeCountDesc 테스트")
    void findTop3ByOrderByLikeCountDesc() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board0 =
                Board.builder()
                        .user(user)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .fileURL(TEST_MAP_IMAGE)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .likeCount(0)
                        .reportCount(TEST_REPORT_COUNT)
                        .build();

        Board board1 =
                Board.builder()
                        .user(user)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .fileURL(TEST_MAP_IMAGE)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .likeCount(1)
                        .reportCount(TEST_REPORT_COUNT)
                        .build();

        Board board2 =
                Board.builder()
                        .user(user)
                        .title(TEST_BOARD_TITLE)
                        .content(TEST_BOARD_CONTENT)
                        .fileURL(TEST_MAP_IMAGE)
                        .departure(TEST_DEPARTURE)
                        .destination(TEST_DESTINATION)
                        .stopover(TEST_STOPOVER)
                        .region(TEST_REGION1)
                        .likeCount(2)
                        .reportCount(TEST_REPORT_COUNT)
                        .build();

        boardRepository.save(board0);
        boardRepository.save(board1);
        boardRepository.save(board2);

        // when
        List<Board> top3Boards = boardRepository.findTop3ByReportCountLessThanOrderByLikeCountDesc(5);

        // then
        assertEquals(3, top3Boards.size());
        assertEquals(TEST_BOARD2.getLikeCount(), top3Boards.get(0).getLikeCount());
        assertEquals(TEST_BOARD1.getLikeCount(), top3Boards.get(1).getLikeCount());
        assertEquals(TEST_BOARD.getLikeCount(), top3Boards.get(2).getLikeCount());
    }

    //    @Test
    //    @DisplayName("findTop3ByOrderByLikeCountDesc 인덱스 적용 테스트")
    //    void findTop3ByOrderByLikeCountDescTest() {
    //        // given
    //        User user = userRepository.save(TEST_USER);
    //
    //        long insertStartTime = System.currentTimeMillis();
    //
    //        for(int i=0; i<1000; i++){ // 1000개 board 저장
    //            Board board =
    //                Board.builder()
    //                    .user(user)
    //                    .title(TEST_BOARD_TITLE)
    //                    .content(TEST_BOARD_CONTENT)
    //                    .fileURL(TEST_MAP_IMAGE)
    //                    .departure(TEST_DEPARTURE)
    //                    .destination(TEST_DESTINATION)
    //                    .stopover(TEST_STOPOVER)
    //                    .region(TEST_REGION1)
    //                    .likeCount(0)
    //                    .reportCount(TEST_REPORT_COUNT)
    //                    .build();
    //            boardRepository.save(board);
    //        }
    //
    //        long insertEndTime = System.currentTimeMillis();
    //        long insertTime = insertEndTime - insertStartTime;
    //
    //        long updateStartTime = System.currentTimeMillis();
    //
    //        // 사용자 활동 시뮬레이션: likeCount 증가 및 감소
    //        for(long i= 1L; i<=1000; i++){
    //            Board findBoard = boardRepository.findById(i);
    //            findBoard.setLikeCount(findBoard.getLikeCount() + 1); // likeCount 1 증가
    //        }
    //
    //        long updateEndTime = System.currentTimeMillis();
    //        long updateTime = updateEndTime - updateStartTime;
    //
    //        // when
    //        long selectStartTime = System.currentTimeMillis();
    //
    //        List<Board> top3Boards = new ArrayList<>();
    //        for(int i=0; i<2000; i++){ // 조회
    //            top3Boards = boardRepository.findTop3ByReportCountLessThanOrderByLikeCountDesc(5);
    //        }
    //
    //        long selectEndTime = System.currentTimeMillis();
    //        long selectTime = selectEndTime - selectStartTime;
    //
    //        System.out.println("삽입 소요 시간: " + insertTime + " milliseconds");
    //        System.out.println("수정 소요 시간: " + updateTime + " milliseconds");
    //        System.out.println("조회 소요 시간: " + selectTime + " milliseconds");
    //
    //        // then
    //        assertEquals(3, top3Boards.size());
    //        assertEquals(1, top3Boards.get(0).getLikeCount());
    //        assertEquals(1, top3Boards.get(1).getLikeCount());
    //        assertEquals(1, top3Boards.get(2).getLikeCount());
    //    }

    @Test
    @DisplayName("findAllByRegion 테스트")
    void findAllByRegionTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        Pageable pageable = PageRequest.ofSize(1);

        // when
        Page<Board> boardPage =
                boardRepository.findAllByReportCountLessThanAndRegion(5, board.getRegion(), pageable);

        // then
        assertEquals(TEST_BOARD_TITLE, boardPage.getContent().get(0).getTitle());
        assertEquals(TEST_BOARD_CONTENT, boardPage.getContent().get(0).getContent());
        assertEquals(TEST_REGION1, boardPage.getContent().get(0).getRegion());
        assertEquals(TEST_LIKE_COUNT, boardPage.getContent().get(0).getLikeCount());
        assertEquals(TEST_REPORT_COUNT, boardPage.getContent().get(0).getReportCount());
    }

    @Test
    @DisplayName("findAllUserBoardByUserId 테스트")
    void findAllUserBoardByUserIdTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        Pageable pageable = PageRequest.ofSize(1);

        // when
        Page<Board> boardPage = boardRepository.findAllByUserId(user.getId(), pageable);

        // then
        assertEquals(board.getTitle(), boardPage.getContent().get(0).getTitle());
        assertEquals(board.getContent(), boardPage.getContent().get(0).getContent());
        assertEquals(board.getRegion(), boardPage.getContent().get(0).getRegion());
        assertEquals(board.getLikeCount(), boardPage.getContent().get(0).getLikeCount());
        assertEquals(board.getReportCount(), boardPage.getContent().get(0).getReportCount());
    }

    @Test
    @DisplayName("findAllByReportCountLessThan 테스트")
    void findAllByReportCountLessThanTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        Pageable pageable = PageRequest.ofSize(1);

        // when
        Page<Board> boardPage = boardRepository.findAllByReportCountLessThan(5, pageable);

        // then
        assertEquals(board.getTitle(), boardPage.getContent().get(0).getTitle());
        assertEquals(board.getContent(), boardPage.getContent().get(0).getContent());
        assertEquals(board.getRegion(), boardPage.getContent().get(0).getRegion());
        assertEquals(board.getLikeCount(), boardPage.getContent().get(0).getLikeCount());
        assertEquals(board.getReportCount(), boardPage.getContent().get(0).getReportCount());
    }
}
