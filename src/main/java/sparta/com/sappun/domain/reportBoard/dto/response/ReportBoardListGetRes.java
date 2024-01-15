package sparta.com.sappun.domain.reportBoard.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBoardListGetRes {
    private List<ReportBoardGetRes> reportBoards;

    @Builder
    private ReportBoardListGetRes(List<ReportBoardGetRes> reportBoards) {
        this.reportBoards = reportBoards;
    }
}
