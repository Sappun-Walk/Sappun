package sparta.com.sappun.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로필 수정 응답 Dto
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileUpdateRes {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String profileUrl;
    private Integer score;

    @Builder
    private UserProfileUpdateRes(
            Long id, String username, String nickname, String email, String profileUrl, Integer score) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
        this.score = score;
    }
}
