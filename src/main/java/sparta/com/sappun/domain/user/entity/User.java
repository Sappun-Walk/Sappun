package sparta.com.sappun.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.com.sappun.domain.TimeStamp;
import sparta.com.sappun.domain.user.dto.request.UserProfileUpdateReq;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_user")
public class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username; // 사용자 아이디

    @Column(unique = true)
    private String nickname; // 사용자 닉네임

    @Column(unique = true)
    private String email;

    private String password;

    private String profileUrl; // 프로필 사진 URL

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private Integer score = 0;

    @Builder
    private User(
            String username,
            String nickname,
            String email,
            String password,
            String profileUrl,
            Role role,
            Integer score) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileUrl = profileUrl;
        this.role = role;
        this.score = score;
    }

    // 프로필 수정
    public void updateProfile(UserProfileUpdateReq req) {
        this.username = req.getUsername();
        this.nickname = req.getNickname();
        this.profileUrl = req.getProfileUrl();
    }

    // 비밀번호 수정
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    // 점수 업데이트
    public void updateScore(Integer point) {
        this.score += point;
        if (score > 10000) score = 10000; // 점수의 범위는 -10000 ~ 10000 이도록
        else if (score < -10000) score = -10000;
    }
}
