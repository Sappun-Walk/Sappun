package sparta.com.sappun.domain.likeBoard.repository;

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
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.LikeBoardTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikeBoardRepositoryTest implements LikeBoardTest {
    @Autowired private LikeBoardRepository likeBoardRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private BoardRepository boardRepository;

    @Test
    @DisplayName("게시글 좋아요 테스트")
    public void testSave() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);

        // when
        LikeBoard likeBoard = likeBoardRepository.save(TEST_LIKE_BOARD);

        // then
        assertEquals(TEST_USER, likeBoard.getUser());
        assertEquals(TEST_BOARD, likeBoard.getBoard());
    }

    @Test
    @DisplayName("게시글 좋아요 찾기 테스트")
    public void existsLikeBoardByBoardAndUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        likeBoardRepository.save(TEST_LIKE_BOARD);

        // when
        boolean isExist = likeBoardRepository.existsLikeBoardByBoardAndUser(board, user);

        // then
        assertTrue(isExist);
    }

    @Test
    @DisplayName("게시글 좋아요 삭제 테스트")
    public void deleteLikeBoardByBoardAndUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        likeBoardRepository.save(TEST_LIKE_BOARD);

        // when
        likeBoardRepository.deleteLikeBoardByBoardAndUser(board, user);
        boolean isExist = likeBoardRepository.existsLikeBoardByBoardAndUser(board, user);

        // then
        assertFalse(isExist);
    }

    @Test
    @DisplayName("게시글 좋아요 사용자로 찾기 테스트")
    public void selectLikeBoardByUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        likeBoardRepository.save(TEST_LIKE_BOARD);

        // when
        List<LikeBoard> likeBoardList = likeBoardRepository.selectLikeBoardByUser(user);

        // then
        assertEquals(board, likeBoardList.get(0).getBoard());
    }

    @Test
    @DisplayName("게시글 좋아요 목록 삭제 테스트")
    public void deleteAllTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        Board board1 = boardRepository.save(TEST_BOARD1);
        LikeBoard likeBoard1 = LikeBoard.builder().board(board1).user(user).build();
        LikeBoard saveLikeBoard = likeBoardRepository.save(TEST_LIKE_BOARD);
        LikeBoard saveLikeBoard1 = likeBoardRepository.save(likeBoard1);

        // when
        likeBoardRepository.deleteAll(List.of(saveLikeBoard, saveLikeBoard1));
        boolean isExist = likeBoardRepository.existsLikeBoardByBoardAndUser(board, user);

        // then
        assertFalse(isExist);
    }

    @Test
    @DisplayName("사용자로 좋아요 찾아 페이징 테스트")
    public void findAllByUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        Board board = boardRepository.save(TEST_BOARD);
        LikeBoard likeBoard = likeBoardRepository.save(TEST_LIKE_BOARD);
        Pageable pageable = PageRequest.ofSize(1);

        // when
        Page<Board> boardPage = boardRepository.findLikeBoardsByUserId(user.getId(), pageable);

        // then
        assertEquals(1, boardPage.getTotalElements());
        assertEquals(board, boardPage.getContent().get(0));
    }
}
