package sparta.com.sappun.domain.user.dto.response;

import lombok.*;

// 프로필 응답 DTO
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileRes {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String profileUrl;
    // private Integer score; // 좋아요(+1) + 신고(-1)

    @Builder
    private UserProfileRes(
            Long id, String username, String nickname, String email, String profileUrl) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.profileUrl = profileUrl;
    }
}
