package sparta.com.sappun.domain.comment.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.CommentTest;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest implements CommentTest {

    @Autowired private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save 테스트")
    void saveTest() {
        // given

        // when
        Comment saveComment = commentRepository.save(TEST_COMMENT);

        // thens
        assertEquals(TEST_COMMENT_CONTENT, saveComment.getContent());
    }
}
