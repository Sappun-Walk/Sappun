package sparta.com.sappun.domain.ReportBoard.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sparta.com.sappun.domain.board.entity.Board;
import sparta.com.sappun.domain.user.entity.User;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBoardReq {

    private Long reportBoardId;
    @NotBlank
    private String reason;
    private Long userId;

    @Builder
    private ReportBoardReq(Long reportBoardId, String reason) {
        this.reportBoardId = reportBoardId;
        this.reason = reason;
    }

}

