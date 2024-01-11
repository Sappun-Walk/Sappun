package sparta.com.sappun.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginReq {

    private String username;
    private String password;

    @Builder
    private UserLoginReq(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
