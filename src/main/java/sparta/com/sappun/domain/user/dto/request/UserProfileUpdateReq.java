package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

// 유저 정보 수정 요청 DTO
// 요청 시, 변경하지 않은 필드라면 원래 필드 값을 출력해야 한다
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileUpdateReq {

    private Long id;

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String username;

    @Size(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$")
    private String nickname;

    private String profileUrl;

    @Builder
    private UserProfileUpdateReq(String username, String nickname, String profileUrl) {
        this.username = username;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
    }
}
