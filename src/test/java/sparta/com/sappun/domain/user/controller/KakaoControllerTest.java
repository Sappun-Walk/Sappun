package sparta.com.sappun.domain.user.controller;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.user.service.KakaoService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

@WebMvcTest(controllers = KakaoController.class)
public class KakaoControllerTest extends BaseMvcTest {

    @MockBean private KakaoService kakaoService;
    @MockBean private JwtUtil jwtUtil;
    @MockBean private RedisUtil redisUtil;

    @Test
    @DisplayName("getKakaoLoginPage 테스트")
    void testGetKakaoLoginPage() throws Exception {
        // given
        Mockito.when(kakaoService.getKakaoLoginPage()).thenReturn("/api/users/kakao/page");

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users/kakao/page"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("kakaoLogin 테스트 - 성공")
    void kakaoLogin_Success() throws Exception {
        // given
        Mockito.when(kakaoService.kakaoLogin(Mockito.anyString())).thenReturn(createMockTokens());

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users/kakao/callback").param("code", "mockCode"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/api/boards/best"));
    }

    private HashMap<String, String> createMockTokens() {
        HashMap<String, String> mockTokens = new HashMap<>();
        mockTokens.put("mockAccessToken", "mockRefreshToken");
        return mockTokens;
    }
}
