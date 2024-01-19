package sparta.com.sappun.domain.user.dto.request;

import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileUpdateReq {

    private Long id;

    @Nullable private String username;

    @Nullable private String nickname;

    private MultipartFile image;

    @Builder
    private UserProfileUpdateReq(@Nullable String username, @Nullable String nickname) {
        if (username != null) this.username = username;
        if (nickname != null) this.nickname = nickname;
    }
}
