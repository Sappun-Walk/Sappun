package sparta.com.sappun.domain.user.dto.response;

import lombok.*;

// 프로필 응답 DTO
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileRes {
    private Long id;
    private String username;
    private String nickname;
    private String profileUrl;
    private Integer score;

    @Builder
    private UserProfileRes(
            Long id, String username, String nickname, String profileUrl, Integer score) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.score = score;
    }
}
