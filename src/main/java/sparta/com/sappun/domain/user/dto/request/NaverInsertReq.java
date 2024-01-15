package sparta.com.sappun.domain.user.dto.request;

import com.nimbusds.jose.shaded.gson.JsonElement;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NaverInsertReq {
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static NaverInsertReq of(JsonElement element, HashMap<String, String> tokens) {
        return NaverInsertReq.builder()
                .email(
                        element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString())
                .nickname(
                        element
                                .getAsJsonObject()
                                .get("response")
                                .getAsJsonObject()
                                .get("nickname")
                                .getAsString())
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .build();
    }
}
