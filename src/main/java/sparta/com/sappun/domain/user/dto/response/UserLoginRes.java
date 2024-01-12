package sparta.com.sappun.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.user.entity.Role;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginRes {
    private Long id;
    private String username;
    private String nickname;
    private Role role;
    private Integer score;

    @Builder
    private UserLoginRes(Long id, String username, String nickname, Role role, Integer score) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.role = role;
        this.score = score;
    }
}
