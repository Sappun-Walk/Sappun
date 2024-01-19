package sparta.com.sappun.domain.likeComment.repository;

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
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.LikeCommentTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikeCommentRepositoryTest implements LikeCommentTest {

    @Autowired private LikeCommentRepository likeCommentRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private CommentRepository commentRepository;

    @Autowired private BoardRepository boardRepository;

    @Test
    @DisplayName("댓글 좋아요 저장 테스트")
    public void testSave() {
        // given
        userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        commentRepository.save(TEST_COMMENT);

        // when
        LikeComment likeComment = likeCommentRepository.save(TEST_LIKE_COMMENT);

        // then
        assertEquals(TEST_USER, likeComment.getUser());
        assertEquals(TEST_COMMENT, likeComment.getComment());
    }

    @Test
    @DisplayName("댓글 좋아요 찾기 테스트")
    public void existsLikeCommentByCommentAndUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);
        likeCommentRepository.save(TEST_LIKE_COMMENT);

        // when
        boolean isExist = likeCommentRepository.existsLikeCommentByCommentAndUser(comment, user);

        // then
        assertTrue(isExist);
    }

    @Test
    @DisplayName("댓글 좋아요 삭제 테스트")
    public void deleteLikeCommentByCommentAndUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);
        likeCommentRepository.save(TEST_LIKE_COMMENT);

        // when
        likeCommentRepository.deleteLikeCommentByCommentAndUser(comment, user);
        boolean isExist = likeCommentRepository.existsLikeCommentByCommentAndUser(comment, user);

        // then
        assertFalse(isExist);
    }

    @Test
    @DisplayName("댓글 좋아요 사용자로 찾기 테스트")
    public void selectLikeCommentByUserTest() {
        // given
        User user = userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);
        likeCommentRepository.save(TEST_LIKE_COMMENT);

        // when
        List<LikeComment> likeCommentList = likeCommentRepository.selectLikeCommentByUser(user);

        // then
        assertEquals(comment, likeCommentList.get(0).getComment());
    }

    @Test
    @DisplayName("댓글 좋아요 목록 삭제 테스트")
    public void deleteAllTest() {
        // given
        User user = userRepository.save(TEST_USER);
        boardRepository.save(TEST_BOARD);
        Comment comment = commentRepository.save(TEST_COMMENT);
        LikeComment likeComment = likeCommentRepository.save(TEST_LIKE_COMMENT);

        // when
        likeCommentRepository.deleteAll(List.of(likeComment));
        boolean isExist = likeCommentRepository.existsLikeCommentByCommentAndUser(comment, user);

        // then
        assertFalse(isExist);
    }
}
