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
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sparta.com.sappun.domain.user.dto.request.NaverInsertReq;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.entity.UserSocialEnum;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

@Slf4j(topic = "NAVER Login")
@Service
@RequiredArgsConstructor
public class NaverService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.naver.client-authentication-method}")
    private String naverAuthenticationMethod;

    @Value("${spring.security.oauth2.client.registration.naver.authorization-grant-type}")
    private String naverGrantType;

    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String naverRedirectUri;

    @Value("${spring.security.oauth2.client.provider.naver.authorization-uri}")
    private String naverAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.naver.token-uri}")
    private String naverTokenUri;

    @Value("${spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String naverUserInfoUri;

    @Value("Bearer")
    private String tokenType;

    @Value("${default.image.url}")
    private String defaultProfileImage;

    public String getNaverLoginPage() {
        String state = new BigInteger(130, new SecureRandom()).toString();
        return naverAuthorizationUri
                + "?client_id="
                + naverClientId
                + "&redirect_uri="
                + naverRedirectUri
                + "&response_type=code&state="
                + state;
    }

    public HashMap<String, String> naverLogin(String code) throws JsonProcessingException {
        // HTML에서 인증 코드(code)를 요청하여 전달받음
        HashMap<String, String> tokens = getNaverTokens(code); // 인증 코드로 토큰 요청 getNaverTokens
        // 받은 access 토큰으로 네이버 사용자 정보를 가져옴
        NaverInsertReq userResource = getNaverUserInfo(tokens);
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
                            .social(UserSocialEnum.NAVER)
                            .profileUrl(defaultProfileImage)
                            .build();
            user = userRepository.save(newUser);
        }
        // 반환할 토큰 생성
        HashMap<String, String> returnTokens = new HashMap<>();
        String accessToken = jwtUtil.createAccessToken(user.getId(), String.valueOf(user.getRole()));
        String refreshToken = jwtUtil.createRefreshToken();
        returnTokens.put(ACCESS_TOKEN_HEADER, accessToken);
        returnTokens.put(REFRESH_TOKEN_HEADER, refreshToken);

        // refresh token 저장
        redisUtil.set(
                jwtUtil.getTokenWithoutBearer(returnTokens.get(REFRESH_TOKEN_HEADER)),
                user.getId(),
                REFRESH_TOKEN_EXPIRED_TIME);

        return returnTokens;
    }

    public HashMap<String, String> getNaverTokens(String code) {
        String accessToken = "";
        String refreshToken = "";

        HashMap<String, String> keyAndValues = new HashMap<>();

        keyAndValues.put("tokenUri", naverTokenUri);
        keyAndValues.put("authenticationMethod", naverAuthenticationMethod);
        keyAndValues.put("grantType", naverGrantType);
        keyAndValues.put("clientId", naverClientId);
        keyAndValues.put("clientSecret", naverClientSecret);
        keyAndValues.put("redirectUri", naverRedirectUri);
        keyAndValues.put("state", new BigInteger(130, new SecureRandom()).toString());

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
            sb.append("&state=" + keyAndValues.get("state"));
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
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    public NaverInsertReq getNaverUserInfo(HashMap<String, String> tokens) {
        String userInfoUri = "";
        String authenticationMethod = "";

        userInfoUri = naverUserInfoUri;
        authenticationMethod = naverAuthenticationMethod;

        JsonElement element = null;

        try {
            URL url = new URL(userInfoUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod(authenticationMethod);
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", tokenType + " " + tokens.get("accessToken"));

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

        return NaverInsertReq.of(element, tokens);
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
