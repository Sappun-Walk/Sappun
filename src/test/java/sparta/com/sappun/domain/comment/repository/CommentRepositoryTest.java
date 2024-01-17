package sparta.com.sappun.domain.comment.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.CommentTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest implements CommentTest {

    @Autowired private CommentRepository commentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BoardRepository boardRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);

        // when
        Comment saveComment = commentRepository.save(TEST_COMMENT);

        // thens
        assertEquals(TEST_USER.getNickname(), saveComment.getUser().getNickname());
        assertEquals(TEST_COMMENT.getContent(), saveComment.getContent());
        assertEquals(TEST_COMMENT.getFileURL(), saveComment.getFileURL());
    }

    @Test
    @DisplayName("findById 테스트")
    void findByIdTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);

        // when
        Comment findComment = commentRepository.findById(comment.getId());

        // then
        assertEquals(findComment.getContent(), TEST_COMMENT.getContent());
    }

    @Test
    @DisplayName("delete 테스트")
    void deleteTest() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);

        // when
        // 데이터베이스에서 삭제
        commentRepository.delete(comment);
        Comment findComment = commentRepository.findById(comment.getId());

        // then
        assertNull(findComment);
    }
}
