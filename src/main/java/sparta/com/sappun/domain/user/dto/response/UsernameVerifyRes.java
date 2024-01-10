package sparta.com.sappun.domain.user.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsernameVerifyRes {
    private Boolean isDuplicated;

    @Builder
    public UsernameVerifyRes(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
