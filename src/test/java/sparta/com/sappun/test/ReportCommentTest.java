package sparta.com.sappun.test;

import sparta.com.sappun.domain.ReportComment.dto.request.ReportCommentReq;
import sparta.com.sappun.domain.ReportComment.entity.ReportComment;

public interface ReportCommentTest extends CommentTest, UserTest {

    String TEST_COMMENT_REASON = "신고 테스트";

    ReportComment REPORT_COMMENT =
            ReportComment.builder()
                    .comment(TEST_COMMENT)
                    .user(TEST_USER)
                    .reason(TEST_COMMENT_REASON)
                    .build();

    ReportCommentReq TEST_REPORT_COMMENT_REQ =
            ReportCommentReq.builder()
                    .reportCommentId(TEST_COMMENT_ID)
                    .userId(TEST_USER_ID)
                    .reason(TEST_COMMENT_REASON)
                    .build();
}
