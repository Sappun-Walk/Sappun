package sparta.com.sappun.domain.user.dto.response;

import lombok.Getter;
import lombok.Setter;
import sparta.com.sappun.domain.user.entity.User;

//프로필 응답 DTO
@Getter
@Setter
public class UserProfileRes {
    private Long userId;
    private String username;
    private String nickname;
    private String profileUrl;
    // private Integer score; // 좋아요(+1) + 신고(-1)

    public UserProfileRes(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
    }
}
