package sparta.com.sappun.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.user.dto.request.UserLoginReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserProfileRes;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignupRes signup(UserSignupReq req) {
        UserValidator.validate(req); // 재확인 비밀번호가 일치하는지 확인 (중복검사는 api를 따로 분리했으므로 추가하지 않음)

        // TODO: 프로필 사진 관련 로직 추가

        userRepository.save(
                User.builder()
                        .username(req.getUsername())
                        .nickname(req.getNickname())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .role(Role.USER)
                        .build());

        return new UserSignupRes();
    }

    @Transactional(readOnly = true)
    public UserLoginRes login(UserLoginReq req) {

        User user = userRepository.findByUsername(req.getUsername());
        UserValidator.validate(user); // 사용자가 존재하는지 확인

        UserValidator.checkPassword(
                passwordEncoder.matches(req.getPassword(), user.getPassword())); // 비밀번호가 일치하는지 확인

        return UserServiceMapper.INSTANCE.toUserLoginRes(user);
    }

    //프로필 조회
    @Transactional
    public UserProfileRes getProfile(Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        return UserServiceMapper.INSTANCE.toUserProfileRes(user);
    }
}
