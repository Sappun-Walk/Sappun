package sparta.com.sappun.domain.user.dto.response;

import lombok.*;
import sparta.com.sappun.domain.user.entity.Role;

// 프로필 응답 DTO
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileRes {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String profileUrl;
    private Role role;
    private Integer score;

    @Builder
    private UserProfileRes(
            Long id,
            String username,
            String nickname,
            String email,
            String profileUrl,
            Role role,
            Integer score) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.role = role;
        this.score = score;
    }
}
