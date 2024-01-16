package sparta.com.sappun.test;

import sparta.com.sappun.domain.comment.entity.Comment;

public interface CommentTest extends BoardTest {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "test content";
    String TEST_COMMENT_FILEURL = "TEST URL";
    String TEST_COMMENT_UPDATE_CONTENT = "update content";

    Comment TEST_COMMENT =
            Comment.builder()
                    .content(TEST_COMMENT_CONTENT)
                    .fileURL(TEST_COMMENT_FILEURL)
                    .user(TEST_USER)
                    .board(TEST_BOARD)
                    .build();
}
