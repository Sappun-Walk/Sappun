package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupReq {

    @Size(min=4, max=10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String username;

    @Size(min=2, max=10)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$")
    private String nickname;

    @Size(min=6, max=15)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]$") //영어 대소문자, 숫자, 특수문자를 각 하나씩 포함해야 함
    private String password;

    private String confirmPassword;

    @Builder
    private UserSignupReq(String username, String nickname, String password,
        String confirmPassword) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
