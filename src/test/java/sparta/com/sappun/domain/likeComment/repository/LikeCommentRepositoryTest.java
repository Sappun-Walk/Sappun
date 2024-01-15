package sparta.com.sappun.domain.likeComment.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.LikeCommentTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikeCommentRepositoryTest implements LikeCommentTest {

    @Autowired private LikeCommentRepository likeCommentRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private CommentRepository commentRepository;

    @Test
    public void testSave() {

        // given
        userRepository.save(TEST_USER);
        commentRepository.save(TEST_COMMENT);

        // when
        LikeComment likeComment = likeCommentRepository.save(TEST_LIKE_COMMENT);

        // thens
        assertEquals(TEST_USER, likeComment.getUser());
        assertEquals(TEST_COMMENT, likeComment.getComment());
    }
}
