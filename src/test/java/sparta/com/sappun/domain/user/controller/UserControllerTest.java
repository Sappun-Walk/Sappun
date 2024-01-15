package sparta.com.sappun.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.user.dto.request.UserLoginReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserDeleteRes;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;
import sparta.com.sappun.test.UserTest;

@WebMvcTest(controllers = {UserController.class})
class UserControllerTest extends BaseMvcTest implements UserTest {

    @MockBean private UserService userService;
    @MockBean private JwtUtil jwtUtil;
    @MockBean private RedisUtil redisUtil;

    @Test
    @DisplayName("signup 테스트")
    void signupTest() throws Exception {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .email(TEST_USER_EMAIL)
                        .password(TEST_USER_PASSWORD)
                        .confirmPassword(TEST_USER_PASSWORD)
                        .build();

        UserSignupRes res = new UserSignupRes();

        // when
        when(userService.signup(any(), any())).thenReturn(res);

        // then
        mockMvc
                .perform(
                        post("/api/users/signup")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("login 테스트")
    void loginTest() throws Exception {
        // given
        UserLoginReq req =
                UserLoginReq.builder().username(TEST_USER_USERNAME).password(TEST_USER_PASSWORD).build();

        UserLoginRes res =
                UserLoginRes.builder()
                        .id(TEST_USER_ID)
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .role(Role.USER)
                        .build();

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        // when
        when(userService.login(any())).thenReturn(res);
        when(jwtUtil.createAccessToken(any(), any())).thenReturn(accessToken);
        when(jwtUtil.createAccessToken(any(), any())).thenReturn(refreshToken);

        // then
        mockMvc
                .perform(
                        post("/api/users/login")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("logout 테스트")
    void logoutTest() throws Exception {
        // given
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        // when
        when(jwtUtil.getTokenWithoutBearer(any())).thenReturn(accessToken);
        when(jwtUtil.getTokenWithoutBearer(any())).thenReturn(refreshToken);

        // then
        mockMvc
                .perform(
                        post("/api/users/logout")
                                .header("AccessToken", "Bearer " + accessToken)
                                .header("RefreshToken", "Bearer " + refreshToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deleteUser 테스트")
    void deleteUserTest() throws Exception {
        // given
        UserDeleteRes res = new UserDeleteRes();

        // when
        when(userService.deleteUser(any())).thenReturn(res);

        // then
        mockMvc
                .perform(delete("/api/users").principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로필 조회 테스트")
    void getProfileTest() {
        // given - 필요한 변수 생성

        // when - 테스트할 메서드를 실제 동작

        // then - 결과 제대로 나왔는지 확인

    }
}
