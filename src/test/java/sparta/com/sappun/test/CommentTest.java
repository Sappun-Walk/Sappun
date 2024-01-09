package sparta.com.sappun.test;

import sparta.com.sappun.domain.comment.entity.Comment;

public interface CommentTest extends UserTest{

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "test content";
    String TEST_COMMENT_FILEURL = "TEST URL";

    Comment TEST_COMMENT =
            Comment.builder()
                    .id(TEST_COMMENT_ID)
                    .nickname(TEST_USER_NICKNAME)
                    .content(TEST_COMMENT_CONTENT)
                    .fileUrl(TEST_COMMENT_FILEURL)
                    .build();
}
