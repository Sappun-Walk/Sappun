package sparta.com.sappun.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoRes {
    private Long id;
    private String nickname;
    private String email;

    public KakaoUserInfoRes(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }
}
