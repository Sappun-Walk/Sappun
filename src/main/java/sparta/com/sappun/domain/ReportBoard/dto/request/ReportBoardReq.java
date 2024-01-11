package sparta.com.sappun.domain.ReportBoard.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
