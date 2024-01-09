package sparta.com.sappun.test;

import sparta.com.sappun.domain.comment.entity.Comment;

public interface CommentTest {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_NICKNAME = "nickname";
    String TEST_COMMENT_CONTENT = "test content";
    String TEST_COMMENT_FILEURL = "TEST URL";

    Comment TEST_COMMENT =
            Comment.builder()
                    .id(TEST_COMMENT_ID)
                    .nickname(TEST_COMMENT_NICKNAME)
                    .content(TEST_COMMENT_CONTENT)
                    .fileUrl(TEST_COMMENT_FILEURL)
                    .build();
}
