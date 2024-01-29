package sparta.com.sappun.domain.user.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.domain.user.service.KakaoService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

import static org.junit.jupiter.api.Assertions.*;

class KakaoControllerTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisUtil redisUtil;

    @InjectMocks
    private KakaoService kakaoService;

    @Test
    void getKakaoLoginPage() {
    }

    @Test
    void kakaoLogin() {
    }
}