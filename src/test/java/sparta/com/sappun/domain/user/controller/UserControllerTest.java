package sparta.com.sappun.domain.user.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sparta.com.sappun.domain.BaseMvcTest;
import sparta.com.sappun.domain.user.dto.request.*;
import sparta.com.sappun.domain.user.dto.response.*;
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

    static MockMultipartFile multipartFile;

    @BeforeAll
    static void setUpProfile() throws IOException {
        String imageUrl = "images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @Test
    @DisplayName("signup 테스트")
    void signupTest() throws Exception {
        // given
        UserSignupReq userSignupReq =
                UserSignupReq.builder()
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .email(TEST_USER_EMAIL)
                        .password(TEST_USER_PASSWORD)
                        .confirmPassword(TEST_USER_PASSWORD)
                        .build();

        MockMultipartFile req =
                new MockMultipartFile(
                        "data",
                        null,
                        "application/json",
                        objectMapper.writeValueAsString(userSignupReq).getBytes(StandardCharsets.UTF_8));

        UserSignupRes res = new UserSignupRes();

        when(userService.signup(any(), any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.POST, "/api/users/signup")
                                .file(multipartFile)
                                .file(req)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
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

        when(userService.login(any())).thenReturn(res);
        when(jwtUtil.createAccessToken(any(), any())).thenReturn(accessToken);
        when(jwtUtil.createAccessToken(any(), any())).thenReturn(refreshToken);

        // when - then
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

        when(jwtUtil.getTokenWithoutBearer(any())).thenReturn(accessToken);
        when(jwtUtil.getTokenWithoutBearer(any())).thenReturn(refreshToken);

        // when - then
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
        when(userService.deleteUser(any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(delete("/api/users").principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로필 조회 테스트")
    void getProfileTest() throws Exception {
        // given - 필요한 변수 생성
        UserProfileRes res =
                UserProfileRes.builder()
                        .id(TEST_USER_ID)
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .role(Role.USER)
                        .build();

        when(userService.getProfile(TEST_USER_ID)).thenReturn(res);

        // when - then - 테스트할 메서드를 실제 동작 & 결과 제대로 나왔는지 확인
        mockMvc
                .perform(get("/api/users").principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("프로필 수정 테스트")
    void updateProfileTest() throws Exception {
        // given
        UserProfileUpdateReq userProfileUpdateReq =
                UserProfileUpdateReq.builder()
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .build();

        MockMultipartFile req =
                new MockMultipartFile(
                        "data",
                        null,
                        "application/json",
                        objectMapper.writeValueAsString(userProfileUpdateReq).getBytes(StandardCharsets.UTF_8));

        UserProfileUpdateRes res =
                UserProfileUpdateRes.builder()
                        .id(TEST_USER_ID)
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .email(TEST_USER_EMAIL)
                        .profileUrl(TEST_USER_PROFILE_URL)
                        .role(Role.USER)
                        .score(0)
                        .build();

        when(userService.updateProfile(any(), any())).thenReturn(res);

        // when - then
        mockMvc
                .perform(
                        MockMvcRequestBuilders.multipart(HttpMethod.PATCH, "/api/users/profile")
                                .file(multipartFile)
                                .file(req)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("비밀번호 수정 테스트")
    void updatePasswordTest() throws Exception {
        // given - 필요한 변수 생성
        UserPasswordUpdateReq req =
                UserPasswordUpdateReq.builder()
                        .prePassword(TEST_USER_PASSWORD)
                        .newPassword(TEST_USER_NEW_PASSWORD)
                        .confirmPassword(TEST_USER_NEW_PASSWORD)
                        .build();

        UserPasswordUpdateRes res = new UserPasswordUpdateRes();

        when(userService.updatePassword(req)).thenReturn(res);

        // when - then - 테스트할 메서드를 실제 동작 & 결과 제대로 나왔는지 확인
        mockMvc
                .perform(
                        patch("/api/users/profile/password")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디 중복 테스트")
    void verifyUsernameTest() throws Exception {
        // given - 필요한 변수 생성
        UsernameVerifyReq req = UsernameVerifyReq.builder().username(TEST_USER_USERNAME).build();

        UsernameVerifyRes res = UsernameVerifyRes.builder().isDuplicated(true).build();

        when(userService.verifyUsername(req)).thenReturn(res);

        // when - then - 테스트할 메서드를 실제 동작 & 결과 제대로 나왔는지 확인
        mockMvc
                .perform(
                        post("/api/users/username")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    void verifyNicknameTest() throws Exception {
        // given - 필요한 변수 생성
        NicknameVerifyReq req = NicknameVerifyReq.builder().nickname(TEST_USER_NICKNAME).build();

        NicknameVerifyRes res = NicknameVerifyRes.builder().isDuplicated(true).build();

        when(userService.verifyNickname(req)).thenReturn(res);

        // when - then - 테스트할 메서드를 실제 동작 & 결과 제대로 나왔는지 확인
        mockMvc
                .perform(
                        post("/api/users/nickname")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(mockPrincipal))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
