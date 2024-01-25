package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NicknameVerifyReq {
    @Size(min = 2, max = 10, message = "최소 2글자, 최대 10글자로 입력해주세요")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$" , message = "닉네임에 특수문자는 사용할 수 없습니다.")
    private String nickname;

    @Builder
    private NicknameVerifyReq(String nickname) {
        this.nickname = nickname;
    }
}
