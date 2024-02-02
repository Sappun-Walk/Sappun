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
import sparta.com.sappun.domain.user.service.NaverService;

@WebMvcTest(controllers = NaverController.class)
public class NaverControllerTest extends BaseMvcTest {

    @MockBean private NaverService naverService;

    @Test
    @DisplayName("getNaverLoginPage 테스트")
    void getNaverLoginPage() throws Exception {
        // given
        Mockito.when(naverService.getNaverLoginPage()).thenReturn("/api/users/naver/page");

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users/naver/page"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("naverLogin 테스트 - 성공")
    void naverLogin_Success() throws Exception {
        // given
        Mockito.when(naverService.naverLogin(Mockito.anyString())).thenReturn(createMockTokens());

        // when & then
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/users/naver/callback").param("code", "mockCode"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/api/boards/best"));
    }

    private HashMap<String, String> createMockTokens() {
        HashMap<String, String> mockTokens = new HashMap<>();
        mockTokens.put("mockAccessToken", "mockRefreshToken");
        return mockTokens;
    }
}
