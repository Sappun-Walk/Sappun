package sparta.com.sappun.test;

import sparta.com.sappun.domain.ReportBoard.entity.ReportBoard;

public interface ReportBoardTest extends UserTest, LikeBoardTest {
    String TEST_BOARD_REPORT_REASON = "게시글 신고 테스트";

    ReportBoard REPORT_BOARD =
            ReportBoard.builder()
                    .board(TEST_BOARD)
                    .user(TEST_USER)
                    .reason(TEST_BOARD_REPORT_REASON)
                    .build();
}
