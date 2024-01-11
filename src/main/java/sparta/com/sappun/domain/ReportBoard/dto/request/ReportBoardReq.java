package sparta.com.sappun.domain.ReportBoard.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.User;


@Data
@NoArgsConstructor
public class ReportBoardReq {

    private Long reportBoardId;
    private String reason;

    @Builder
    public ReportBoardReq(Long reportBoardId, String reason) {
        this.reportBoardId = reportBoardId;
        this.reason = reason;
;
    }

}

