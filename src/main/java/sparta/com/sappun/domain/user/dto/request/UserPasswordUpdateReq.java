package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

// 비밀번호 수정 요청 DTO
// [기존비밀번호, 바꿀 비밀번호, 바꿀 비밀번호 검증] 입력 후 새 비밀번호로 변경
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPasswordUpdateReq {

    static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]*$";

    private Long id;

    @Size(min = 6, max = 15, message = "최소 6글자, 최대 15글자로 입력해주세요")
    @Pattern(regexp = PASSWORD_PATTERN, message = "영어 대소문자 및 숫자와 특수문자를 각 하나 씩은 포함해 입력해주세요")
    // 영어 소문자, 숫자, 특수문자를 각 하나씩 필수로 포함
    // 이전 비밀번호를 입력해야함
    private String prePassword;

    @Size(min = 6, max = 15, message = "최소 6글자, 최대 15글자로 입력해주세요")
    @Pattern(regexp = PASSWORD_PATTERN, message = "영어 대소문자 및 숫자와 특수문자를 각 하나 씩은 포함해 입력해주세요")
    private String newPassword;

    @Size(min = 6, max = 15, message = "최소 6글자, 최대 15글자로 입력해주세요")
    @Pattern(regexp = PASSWORD_PATTERN, message = "영어 대소문자 및 숫자와 특수문자를 각 하나 씩은 포함해 입력해주세요")
    private String confirmPassword;

    @Builder
    private UserPasswordUpdateReq(String prePassword, String newPassword, String confirmPassword) {
        this.prePassword = prePassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
}
