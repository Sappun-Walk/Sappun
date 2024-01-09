package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 비밀번호 수정 요청 DTO
// [기존비밀번호, 바꿀 비밀번호, 바꿀 비밀번호 검증] 입력 후 새 비밀번호로 변경
@Getter
@Setter
public class UserPasswordUpdateReq {

    @Size(min = 6, max = 15)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]*$")
    // 영어 소문자, 숫자, 특수문자를 각 하나씩 필수로 포함
    // 이전 비밀번호를 입력해야함
    private String prePassword;

    @Size(min = 6, max = 15)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]*$")
    private String newPassword;

    @Size(min = 6, max = 15)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%#?&])[A-Za-z\\d@$!%*#?&]*$")
    private String confirmPassword;
}
