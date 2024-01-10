package sparta.com.sappun.test;

import sparta.com.sappun.domain.LikeComment.entity.LikeComment;
import sparta.com.sappun.domain.comment.entity.Comment;
import sparta.com.sappun.domain.user.entity.User;

public interface LikeCommentTest extends CommentTest,UserTest{
    LikeComment TEST_LIKE_COMMENT =
            LikeComment.builder().
                    comment(TEST_COMMENT).
                    user(TEST_USER).
                    build();
}
