package sparta.com.sappun.domain.LikeComment.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import sparta.com.sappun.domain.LikeComment.entity.LikeComment;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.test.CommentTest;
import sparta.com.sappun.test.LikeCommentTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LikeCommentRepositoryTest implements LikeCommentTest {


    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testSave() {

        // given
        userRepository.save(TEST_USER);
        commentRepository.save(TEST_COMMENT);
        // BoardRepositoru 추가 필요

        // when
        LikeComment likeComment = likeCommentRepository.save(TEST_LIKE_COMMENT);

        // thens
        assertEquals(TEST_USER, likeComment.getUser());
        assertEquals(TEST_COMMENT,likeComment.getComment());
    }
}
