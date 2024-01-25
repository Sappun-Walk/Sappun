package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameVerifyReq {

    @Size(min = 4, max = 15, message = "최소 4글자, 최대 15글자로 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 대소문자 및 숫자로 입력해주세요.")
    private String username;

    @Builder
    private UsernameVerifyReq(String username) {
        this.username = username;
    }
}
