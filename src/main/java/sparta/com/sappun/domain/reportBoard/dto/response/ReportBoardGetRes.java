package sparta.com.sappun.domain.reportBoard.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.dto.response.BoardToReportGetRes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBoardGetRes {
    private Long id;
    private String nickname; // 신고자
    private String reason;
    private BoardToReportGetRes board;

    @Builder
    private ReportBoardGetRes(Long id, String reason, BoardToReportGetRes board, String nickname) {
        this.id = id;
        this.reason = reason;
        this.board = board;
        this.nickname = nickname;
    }
}
