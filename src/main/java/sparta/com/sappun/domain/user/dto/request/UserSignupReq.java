package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupReq {

    @Size(min = 4, max = 15, message = "최소 4글자, 최대 15글자로 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 대소문자 및 숫자로 입력해주세요.")
    private String username;

    @Size(min = 2, max = 10, message = "최소 2글자, 최대 10글자로 입력해주세요")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$")
    private String nickname;

    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식에 맞게 입력해주세요")
    private String email;

    @Size(min = 6, max = 15, message = "최소 6글자, 최대 15글자로 입력해주세요")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]*$",
            message = "영어 대소문자 및 숫자와 특수문자를 각 하나 씩은 포함해 입력해주세요.")
    // 영어 소문자, 숫자, 특수문자를 각 하나씩 필수로 포함
    private String password;

    private String confirmPassword;

    private MultipartFile image;

    @Builder
    private UserSignupReq(
            String username, String nickname, String email, String password, String confirmPassword) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
