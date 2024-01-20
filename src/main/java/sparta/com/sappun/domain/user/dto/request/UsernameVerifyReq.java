package sparta.com.sappun.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameVerifyReq {

    @Size(min = 4, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String username;

    @Builder
    private UsernameVerifyReq(String username) {
        this.username = username;
    }
}
