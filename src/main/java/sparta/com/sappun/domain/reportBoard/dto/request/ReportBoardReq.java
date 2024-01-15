package sparta.com.sappun.domain.reportBoard.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportBoardReq {

    @NotBlank private String reason;
    private Long userId;

    @Builder
    private ReportBoardReq(String reason) {
        this.reason = reason;
    }
}
