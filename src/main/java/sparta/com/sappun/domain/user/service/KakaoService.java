package sparta.com.sappun.domain.user.service;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static sparta.com.sappun.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;
import static sparta.com.sappun.global.redis.RedisUtil.REFRESH_TOKEN_EXPIRED_TIME;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.user.dto.request.KakaoInsertReq;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.entity.UserSocialEnum;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.client-authentication-method}")
    private String kakaoAuthenticationMethod;

    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String kakaoGrantType;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String kakaoAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String kakaoTokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String kakaoUserInfoUri;

    @Value("Bearer")
    private String tokenType;

    @Value("${default.image.url}")
    private String defaultProfileImage;

    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_REFRESH_TOKEN = "refreshToken";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public String getKakaoLoginPage() {
        return kakaoAuthorizationUri
                + "?client_id="
                + kakaoClientId
                + "&redirect_uri="
                + kakaoRedirectUri
                + "&response_type=code";
    }

    public HashMap<String, String> kakaoLogin(String code) throws JsonProcessingException {
        // HTML에서 인증 코드(code)를 요청하여 전달받음
        HashMap<String, String> tokens = getKakaoTokens(code); // 인증 코드로 토큰 요청 getKakaoTokens
        // 받은 access 토큰으로 카카오 사용자 정보를 가져옴
        KakaoInsertReq userResource = getKakaoUserInfo(tokens);
        // 가져온 사용자 정보 중 이메일을 꺼냄
        String email = userResource.getEmail();
        // @의 위치를 num으로 지정한다
        int num = email.indexOf("@");
        // 이메일의 0번째~num(@의 위치)앞까지 이름으로 따옴
        String name = email.substring(0, num);
        String nickname = userResource.getNickname();
        // 이름과 닉네임이 중복인지 아닌지 확인
        if (userRepository.existsByUsername(name)) {
            name = makeRandomName(name);
        }
        if (userRepository.existsByNickname(nickname)) {
            nickname = makeRandomNickname(nickname);
        }
        // 이메일이 없다면 새로운 user객체로 만들어줌
        User user = userRepository.findByEmail(email);
        if (user == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            User newUser =
                    User.builder()
                            .username(name)
                            .nickname(nickname)
                            .password(encodedPassword)
                            .email(email)
                            .role(Role.USER)
                            .score(0)
                            .social(UserSocialEnum.KAKAO)
                            .profileUrl(defaultProfileImage)
                            .build();
            user = userRepository.save(newUser);
        }
        // 반환할 토큰 생성
        HashMap<String, String> returnTokens = new HashMap<>();
        String accessToken =
                jwtUtil.createAccessToken(String.valueOf(user.getId()), String.valueOf(user.getRole()));
        String refreshToken = jwtUtil.createRefreshToken();
        returnTokens.put(ACCESS_TOKEN_HEADER, accessToken);
        returnTokens.put(REFRESH_TOKEN_HEADER, refreshToken);

        // refresh token 저장
        redisUtil.set(
                jwtUtil.getTokenWithoutBearer(returnTokens.get(REFRESH_TOKEN_HEADER)),
                String.valueOf(user.getId()),
                REFRESH_TOKEN_EXPIRED_TIME);

        return returnTokens;
    }

    public HashMap<String, String> getKakaoTokens(String code) {
        String accessToken = "";
        String refreshToken = "";

        HashMap<String, String> keyAndValues = new HashMap<>();

        keyAndValues.put("tokenUri", kakaoTokenUri);
        keyAndValues.put("authenticationMethod", kakaoAuthenticationMethod);
        keyAndValues.put("grantType", kakaoGrantType);
        keyAndValues.put("clientId", kakaoClientId);
        keyAndValues.put("clientSecret", kakaoClientSecret);
        keyAndValues.put("redirectUri", kakaoRedirectUri);

        try {
            URL url = new URL(keyAndValues.get("tokenUri"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(keyAndValues.get("authenticationMethod"));
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + keyAndValues.get("grantType"));
            sb.append("&client_id=" + keyAndValues.get("clientId"));
            sb.append("&client_secret=" + keyAndValues.get("clientSecret"));
            sb.append("&redirect_uri=" + keyAndValues.get("redirectUri"));
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            br.close();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(KEY_ACCESS_TOKEN, accessToken);
        tokens.put(KEY_REFRESH_TOKEN, refreshToken);

        return tokens;
    }

    public KakaoInsertReq getKakaoUserInfo(HashMap<String, String> tokens) {
        String userInfoUri = "";
        String authenticationMethod = "";

        userInfoUri = kakaoUserInfoUri;
        authenticationMethod = kakaoAuthenticationMethod;

        JsonElement element = null;

        try {
            URL url = new URL(userInfoUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(authenticationMethod);
            conn.setDoOutput(true);
            conn.setRequestProperty(AUTHORIZATION_HEADER, tokenType + " " + tokens.get(KEY_ACCESS_TOKEN));

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            element = parser.parse(result);

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return KakaoInsertReq.of(element, tokens);
    }

    private String makeRandomName(String name) {
        String randomName;
        do {
            randomName = name + UUID.randomUUID().toString().substring(0, 3);
        } while (userRepository.existsByUsername(randomName));
        return randomName;
    }

    private String makeRandomNickname(String nickname) {
        String randomNickname;
        do {
            randomNickname = nickname + UUID.randomUUID().toString().substring(0, 3);
        } while (userRepository.existsByNickname(randomNickname));
        return randomNickname;
    }
}
