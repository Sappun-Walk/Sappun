package sparta.com.sappun.test;

import sparta.com.sappun.domain.comment.dto.request.CommentSaveReq;
import sparta.com.sappun.domain.comment.dto.request.CommentUpdateReq;
import sparta.com.sappun.domain.comment.entity.Comment;

public interface CommentTest extends UserTest {

    Long TEST_COMMENT_ID = 1L;
    String TEST_COMMENT_CONTENT = "test content";
    String TEST_COMMENT_FILEURL = "TEST URL";

    String TEST_COMMENT_UPDATE_CONTENT = "update content";

    Comment TEST_COMMENT =
            Comment.builder()
                    .content(TEST_COMMENT_CONTENT)
                    .fileUrl(TEST_COMMENT_FILEURL)
                    .user(TEST_USER)
                    .build();

    CommentSaveReq TEST_COMMENT_SAVE =
            CommentSaveReq.builder()
                    .content(TEST_COMMENT_CONTENT)
                    .fileUrl(TEST_COMMENT_FILEURL)
                    .build();

    CommentUpdateReq TEST_COMMENT_UPDATE =
            CommentUpdateReq.builder()
                    .content(TEST_COMMENT_UPDATE_CONTENT)
                    .fileUrl(TEST_COMMENT_FILEURL)
                    .build();
}
