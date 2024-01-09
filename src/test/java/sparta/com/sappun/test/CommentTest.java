package sparta.com.sappun.test;

import sparta.com.sappun.domain.comment.entity.Comment;

public interface CommentTest {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "test content";

    Comment TEST_COMMENT =
            Comment.builder().id(TEST_COMMENT_ID).content(TEST_COMMENT_CONTENT).build();
}
