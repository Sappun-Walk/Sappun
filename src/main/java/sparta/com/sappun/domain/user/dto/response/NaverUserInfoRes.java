package sparta.com.sappun.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfoRes {
    private Long id;
    private String nickname;
    private String email;
    private String accessToken;
    private String refreshToken;

    public NaverUserInfoRes(
            Long id, String nickname, String email, String accessToken, String refreshToken) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
