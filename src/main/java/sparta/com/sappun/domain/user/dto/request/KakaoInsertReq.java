package sparta.com.sappun.domain.user.dto.request;

import com.nimbusds.jose.shaded.gson.JsonElement;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class KakaoInsertReq {
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static KakaoInsertReq of(JsonElement element, HashMap<String, String> tokens) {
        return KakaoInsertReq.builder()
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
