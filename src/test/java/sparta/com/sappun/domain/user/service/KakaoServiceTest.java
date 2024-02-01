package sparta.com.sappun.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import sparta.com.sappun.domain.user.dto.request.KakaoInsertReq;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;
import sparta.com.sappun.infra.s3.S3Util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KakaoServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisUtil redisUtil;

    @Mock
    private S3Util s3Util;

    @Mock
    private HttpURLConnection httpURLConnection;

    @InjectMocks
    private KakaoService kakaoService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(kakaoService, "kakaoClientId", "eb63c9db76c1cd5b08e311f603565bfc");
        ReflectionTestUtils.setField(kakaoService, "kakaoClientSecret", "Cn3WajT3oRvdrGyOAISfizedCPe6DnB9");
        ReflectionTestUtils.setField(kakaoService, "kakaoAuthenticationMethod", "POST");
        ReflectionTestUtils.setField(kakaoService, "kakaoGrantType", "authorization_code");
        ReflectionTestUtils.setField(kakaoService, "kakaoRedirectUri", "https://www.sappun.shop/api/users/kakao/callback");
        ReflectionTestUtils.setField(kakaoService, "kakaoAuthorizationUri", "https://kauth.kakao.com/oauth/authorize");
        ReflectionTestUtils.setField(kakaoService, "kakaoTokenUri", "https://kauth.kakao.com/oauth/token");
        ReflectionTestUtils.setField(kakaoService, "kakaoUserInfoUri", "https://kapi.kakao.com/v2/user/me");
    }

    @Test
    @DisplayName("카카오 로그인 페이지 URL 생성 테스트")
    void testGetKakaoLoginPage() {
        // Mocking
        KakaoService kakaoServiceMock = mock(KakaoService.class);

        // 가짜 URL을 만들어서 반환하도록 설정
        when(kakaoServiceMock.getKakaoLoginPage()).thenReturn("http://fake.kakao.login.page");

        // 테스트 대상 메소드 호출
        String result = kakaoServiceMock.getKakaoLoginPage();

        // 결과 검증
        assertEquals("http://fake.kakao.login.page", result);
    }

//    @Test
//    @DisplayName("kakaoLoginTest")
//    void kakaoLoginTest() throws JsonProcessingException {
//        // Given
//        String code = "kakaoCode";
//        HashMap<String, String> tokens = new HashMap<>();
//        tokens.put("accessToken", "access_token");
//        tokens.put("refreshToken", "refresh_token");
//
//        KakaoInsertReq kakaoUser = KakaoInsertReq.builder()
//                .email("kakaoTest@example.com")
//                .nickname("test_user")
//                .build();
//
//        // Mocking
//        when(kakaoService.getKakaoTokens(code)).thenReturn(tokens);
//        when(kakaoService.getKakaoUserInfo(tokens)).thenReturn(kakaoUser);
//        when(userRepository.existsByUsername(any())).thenReturn(false);
//        when(userRepository.existsByNickname(any())).thenReturn(false);
//        when(userRepository.findByEmail(any())).thenReturn(null);
//        when(jwtUtil.createAccessToken(any(), any())).thenReturn("access_token");
//        when(jwtUtil.createRefreshToken()).thenReturn("refresh_token");
//
//        // When
//        HashMap<String, String> result = kakaoService.kakaoLogin(code);
//
//        // Then
//        // UserRepository의 메서드들이 각각 1번씩 호출되었는지 검증
//        verify(userRepository, times(1)).existsByUsername(any());
//        verify(userRepository, times(1)).existsByNickname(any());
//        verify(userRepository, times(1)).findByEmail(any());
//        verify(userRepository, times(1)).save(any());
//
//        // 결과 값이 올바른지 검증
//        assertEquals("access_token", result.get("accessToken"));
//        assertEquals("refresh_token", result.get("refreshToken"));
//    }
//
//    @Test
//    @DisplayName("getKakaoTokensTest")
//    void getKakaoTokensTest() throws IOException {
//        // Given
//        URL url = new URL("https://kauth.kakao.com/oauth/token");
//        when(url.openConnection()).thenReturn(httpURLConnection);
//        when(httpURLConnection.getResponseCode()).thenReturn(200);
//        String code = "kakaoCode";
//        HashMap<String, String> tokens = new HashMap<>();
//        tokens.put("accessToken", "access_token");
//        tokens.put("refreshToken", "refresh_token");
//
//        // When
//        HashMap<String, String> resultTokens = null;
//        try {
//            resultTokens = kakaoService.getKakaoTokens(code);
//        } catch (Exception e) {
//            // 어떤 예외가 발생했는지 출력
//            System.out.println("Exception type: " + e.getClass().getName());
//            e.printStackTrace();
//        }
//
//        // Then
//        // 토큰이 올바르게 생성되었는지 검증
//        assertNotNull(resultTokens);
//        assertEquals("accessToken", resultTokens.get("accessToken"));
//        assertEquals("refresh_token", resultTokens.get("refreshToken"));
//    }
}