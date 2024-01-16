package sparta.com.sappun.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static sparta.com.sappun.global.response.ResultCode.*;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.user.dto.request.*;
import sparta.com.sappun.domain.user.dto.response.*;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.exception.GlobalException;
import sparta.com.sappun.infra.s3.S3Util;
import sparta.com.sappun.test.UserTest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements UserTest {
    @Mock UserRepository userRepository;

    @Mock PasswordEncoder passwordEncoder;

    @Mock S3Util s3Util;

    @InjectMocks UserService userService;

    @Captor ArgumentCaptor<User> argumentCaptor;

    static MultipartFile multipartFile;

    @BeforeAll
    static void setUpProfile() throws IOException {
        String imageUrl = "static/images/image1.jpg";
        Resource fileResource = new ClassPathResource(imageUrl);

        multipartFile =
                new MockMultipartFile(
                        "image", fileResource.getFilename(), IMAGE_JPEG_VALUE, fileResource.getInputStream());
    }

    @Test
    @DisplayName("signup 테스트 - 성공")
    void signupTest() {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .email(TEST_USER_EMAIL)
                        .password(TEST_USER_PASSWORD)
                        .confirmPassword(TEST_USER_PASSWORD)
                        .build();

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_USER_PROFILE_URL);

        // when
        userService.signup(req, multipartFile);

        // then
        verify(userRepository).save(argumentCaptor.capture());
        verify(passwordEncoder).encode(TEST_USER_PASSWORD);
        assertEquals(TEST_USER_USERNAME, argumentCaptor.getValue().getUsername());
        assertEquals(TEST_USER_NICKNAME, argumentCaptor.getValue().getNickname());
        assertEquals(TEST_USER_PROFILE_URL, argumentCaptor.getValue().getProfileUrl());
        assertEquals(TEST_USER_EMAIL, argumentCaptor.getValue().getEmail());
        assertEquals(0, argumentCaptor.getValue().getScore());
    }

    @Test
    @DisplayName("signup 테스트 - 확인 비밀번호 일치 실패")
    void signupPasswordFailureTest() {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .email(TEST_USER_EMAIL)
                        .password(TEST_USER_PASSWORD)
                        .confirmPassword("wrongPassword")
                        .build();

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            userService.signup(req, multipartFile);
                        });

        // then
        assertEquals(NOT_MATCHED_PASSWORD.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("signup 테스트 - 이메일 중복 실패")
    void signupEmailFailureTest() {
        // given
        UserSignupReq req =
                UserSignupReq.builder()
                        .username(TEST_USER_USERNAME)
                        .nickname(TEST_USER_NICKNAME)
                        .email(TEST_USER_EMAIL)
                        .password(TEST_USER_PASSWORD)
                        .confirmPassword(TEST_USER_PASSWORD)
                        .build();

        when(userRepository.existsByEmail(any())).thenReturn(true);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            userService.signup(req, multipartFile);
                        });

        // then
        assertEquals(DUPLICATED_EMAIL.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("login 테스트")
    void loginTest() {
        // given
        UserLoginReq req =
                UserLoginReq.builder().username(TEST_USER_USERNAME).password(TEST_USER_PASSWORD).build();

        when(userRepository.findByUsername(any())).thenReturn(TEST_USER);
        ReflectionTestUtils.setField(TEST_USER, "id", TEST_USER_ID);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        // when
        UserLoginRes res = userService.login(req);

        // then
        assertEquals(TEST_USER_ID, res.getId());
        assertEquals(TEST_USER_USERNAME, res.getUsername());
        assertEquals(TEST_USER_NICKNAME, res.getNickname());
        assertEquals(Role.USER, res.getRole());
    }

    @Test
    @DisplayName("deleteUser 테스트")
    void deleteUserTest() {
        // given
        when(userRepository.findById(any())).thenReturn(TEST_USER);

        // when
        userService.deleteUser(TEST_USER_ID);

        // then
        verify(userRepository).findById(any());
        verify(userRepository).delete(any());
    }

    @Test
    @DisplayName("프로필 조회 테스트 - 성공")
    void getProfileTest() {
        // given - 필요한 변수 생성
        when(userRepository.findById(TEST_USER_ID)).thenReturn(TEST_USER);
        ReflectionTestUtils.setField(TEST_USER, "id", TEST_USER_ID);

        // when - 테스트할 메서드를 실제 동작
        UserProfileRes res = userService.getProfile(TEST_USER_ID);

        // then - 결과 제대로 나왔는지 확인
        assertEquals(TEST_USER_ID, res.getId());
        assertEquals(TEST_USER_USERNAME, res.getUsername());
        assertEquals(TEST_USER_NICKNAME, res.getNickname());
        assertEquals(Role.USER, res.getRole());
    }

    @Test
    @DisplayName("프로필 조회 테스트 - 실패(사용자를 찾지 못함)")
    void getProfileFailureTest() {
        // given - 필요한 변수 생성
        when(userRepository.findById(TEST_USER_ID)).thenReturn(null);

        // when
        GlobalException exception =
                assertThrows(
                        GlobalException.class,
                        () -> {
                            userService.getProfile(TEST_USER_ID);
                        });

        // then
        assertEquals(NOT_FOUND_USER.getMessage(), exception.getResultCode().getMessage());
    }

    @Test
    @DisplayName("프로필 수정 테스트")
    void updateProfileTest() {
        // given
        String updatedUsername = "updatedUsername";
        String updatedNickname = "updatedNickname";

        UserProfileUpdateReq req =
                UserProfileUpdateReq.builder().username(updatedUsername).nickname(updatedNickname).build();

        when(userRepository.findById(any())).thenReturn(TEST_USER2);
        ReflectionTestUtils.setField(TEST_USER2, "id", 2L);
        when(s3Util.uploadFile(any(), any())).thenReturn(TEST_USER_PROFILE_URL);

        // when
        UserProfileUpdateRes res = userService.updateProfile(req, multipartFile);

        // then
        assertEquals(2L, res.getId());
        assertEquals(updatedUsername, res.getUsername());
        assertEquals(updatedNickname, res.getNickname());
        assertEquals(TEST_USER_PROFILE_URL, res.getProfileUrl());
    }

    @Test
    @DisplayName("비밀번호 수정 테스트 - 성공")
    void updatePasswordTest() {
        // given - 필요한 변수 생성
        String prePassword = "prePassword";
        String newPassword = "newPassword";

        UserPasswordUpdateReq req =
                UserPasswordUpdateReq.builder()
                        .prePassword(prePassword)
                        .newPassword(newPassword)
                        .confirmPassword(newPassword)
                        .build();

        when(userRepository.findById(any())).thenReturn(TEST_USER);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn(newPassword);

        // when - 테스트할 메서드를 실제 동작
        userService.updatePassword(req);

        // then
        verify(passwordEncoder).encode(newPassword);
    }

    @Test
    @DisplayName("아이디 중복 테스트")
    void verifyUsernameTest() {
        // given - 필요한 변수 생성
        UsernameVerifyReq req = UsernameVerifyReq.builder().username(TEST_USER_USERNAME).build();

        when(userRepository.existsByUsername(any())).thenReturn(true);

        // when - 테스트할 메서드를 실제 동작
        UsernameVerifyRes res = userService.verifyUsername(req);

        // then - 결과 제대로 나왔는지 확인
        assertTrue(res.getIsDuplicated());
    }

    @Test
    @DisplayName("닉네임 중복 테스트")
    void verifyNicknameTest() {
        // given - 필요한 변수 생성
        NicknameVerifyReq req = NicknameVerifyReq.builder().nickname(TEST_USER_NICKNAME).build();

        when(userRepository.existsByNickname(any())).thenReturn(true);

        // when - 테스트할 메서드를 실제 동작
        NicknameVerifyRes res = userService.verifyNickname(req);

        // then - 결과 제대로 나왔는지 확인
        assertTrue(res.getIsDuplicated());
    }
}
