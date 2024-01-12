package sparta.com.sappun.domain.ReportBoard.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.dto.response.BoardGetRes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBoardGetRes {
    private Long id;
    private String reason;
    private BoardGetRes board;

    @Builder
    private ReportBoardGetRes(Long id, String reason, BoardGetRes board) {
        this.id = id;
        this.reason = reason;
        this.board = board;
    }
}
