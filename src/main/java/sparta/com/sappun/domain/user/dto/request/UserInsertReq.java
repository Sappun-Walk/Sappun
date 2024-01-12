package sparta.com.sappun.domain.user.dto.request;

import com.nimbusds.jose.shaded.gson.JsonElement;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInsertReq {
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static UserInsertReq of(JsonElement element, HashMap<String, String> tokens) {
        return UserInsertReq.builder()
                .email(
                        element
                                .getAsJsonObject()
                                .get("kakao_account")
                                .getAsJsonObject()
                                .get("email")
                                .getAsString())
                .nickname(
                        element
                                .getAsJsonObject()
                                .get("kakao_account")
                                .getAsJsonObject()
                                .get("profile")
                                .getAsJsonObject()
                                .get("nickname")
                                .getAsString())
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .build();
    }
}
