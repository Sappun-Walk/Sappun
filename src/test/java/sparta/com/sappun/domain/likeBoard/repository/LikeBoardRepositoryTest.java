package sparta.com.sappun.domain.likeBoard.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
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

        // thens
        assertEquals(TEST_USER, likeBoard.getUser());
        assertEquals(TEST_BOARD, likeBoard.getBoard());
    }
}
