package sparta.com.sappun.domain.board.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardToReportListGetRes {
    private List<BoardToReportListGetRes> boards;

    @Builder
    private BoardToReportListGetRes(List<BoardToReportListGetRes> boards) {
        this.boards = boards;
    }
}
