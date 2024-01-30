package sparta.com.sappun.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class KakaoServiceTest {
    @Mock PasswordEncoder passwordEncoder;
    @Mock UserRepository userRepository;
    @Mock JwtUtil jwtUtil;
    @Mock RedisUtil redisUtil;
    @Mock String kakaoClientId;
    @Mock private String kakaoClientSecret;
    @Mock private String kakaoAuthenticationMethod;
    @Mock private String kakaoGrantType;
    @Mock private String kakaoRedirectUri;
    @Mock private String kakaoAuthorizationUri;
    @Mock private String kakaoTokenUri;
    @Mock private String kakaoUserInfoUri;
    @Mock private String tokenType;
    @Mock private String defaultProfileImage;

    @InjectMocks KakaoService kakaoService;



    @Test
    void getKakaoLoginPage() {
    }

    @Test
    void kakaoLogin() {
    }

    @Test
    void getKakaoTokens() {
    }

    @Test
    void getKakaoUserInfo() {
    }
}