package sparta.com.sappun.test;

import sparta.com.sappun.domain.reportComment.entity.ReportComment;

public interface ReportCommentTest extends CommentTest, UserTest {

    Long TEST_REPORT_COMMENT_ID = 1L;
    String TEST_COMMENT_REPORT_REASON = "신고 테스트";

    ReportComment REPORT_COMMENT =
            ReportComment.builder()
                    .comment(TEST_COMMENT)
                    .user(TEST_USER)
                    .reason(TEST_COMMENT_REPORT_REASON)
                    .build();
}
