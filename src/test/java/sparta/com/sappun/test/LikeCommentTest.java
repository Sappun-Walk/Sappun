package sparta.com.sappun.test;

import sparta.com.sappun.domain.likeComment.entity.LikeComment;

public interface LikeCommentTest extends CommentTest, UserTest {
    LikeComment TEST_LIKE_COMMENT =
            LikeComment.builder().comment(TEST_COMMENT).user(TEST_USER).build();
}
