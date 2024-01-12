package sparta.com.sappun.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sparta.com.sappun.global.response.ResultCode.NOT_MATCHED_PASSWORD;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import sparta.com.sappun.domain.user.dto.request.UserLoginReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.exception.GlobalException;
import sparta.com.sappun.test.UserTest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements UserTest {
    @Mock UserRepository userRepository;

    @Mock PasswordEncoder passwordEncoder;

    @InjectMocks UserService userService;

    @Captor ArgumentCaptor<User> argumentCaptor;

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

        // when
        userService.signup(req);

        // then
        verify(userRepository).save(argumentCaptor.capture());
        verify(passwordEncoder).encode(TEST_USER_PASSWORD);
        assertEquals(TEST_USER_USERNAME, argumentCaptor.getValue().getUsername());
        assertEquals(TEST_USER_NICKNAME, argumentCaptor.getValue().getNickname());
        assertEquals(TEST_USER_EMAIL, argumentCaptor.getValue().getEmail());
    }

    @Test
    @DisplayName("signup 테스트 - 실패")
    void signupFailureTest() {
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
                            userService.signup(req);
                        });

        // then
        assertEquals(NOT_MATCHED_PASSWORD.getMessage(), exception.getResultCode().getMessage());
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

        // when - 테스트할 메서드를 실제 동작

        // then - 결과 제대로 나왔는지 확인
    }

    @Test
    @DisplayName("프로필 조회 테스트 - 실패")
    void getProfileFailureTest() {
        // given - 필요한 변수 생성

        // when - 테스트할 메서드를 실제 동작

        // then - 결과 제대로 나왔는지 확인
    }
}
