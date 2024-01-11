package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameVerifyReq {
    @Size(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$")
    private String nickname;

    @Builder
    private NicknameVerifyReq(String nickname) {
        this.nickname = nickname;
    }
}
