package sparta.com.sappun.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.com.sappun.domain.user.dto.request.*;
import sparta.com.sappun.domain.user.dto.response.*;
import sparta.com.sappun.domain.user.dto.response.UserProfileUpdateRes;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.UserValidator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserSignupRes signup(UserSignupReq req) {
        UserValidator.validate(req);

        UserValidator.checkEmail(userRepository.existsByEmail(req.getEmail()));

        // TODO: 프로필 사진 관련 로직 추가

        userRepository.save(
                User.builder()
                        .username(req.getUsername())
                        .nickname(req.getNickname())
                        .email(req.getEmail())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .role(Role.USER)
                        .score(0)
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

    @Transactional
    public UserDeleteRes deleteUser(Long id) {
        User user = getUserById(id); // 사용자가 존재하는지 확인

        // TODO: 회원 탈퇴 시 회원의 게시글과 댓글, 좋아요 등의 처리를 어떻게 할 것인지 정하기

        userRepository.delete(user);

        return new UserDeleteRes();
    }

    // 프로필 조회
    @Transactional(readOnly = true)
    public UserProfileRes getProfile(Long userId) {
        return UserServiceMapper.INSTANCE.toUserProfileRes(getUserById(userId));
    }

    // 프로필 수정
    @Transactional
    public UserProfileUpdateRes updateProfile(UserProfileUpdateReq req) {
        User user = getUserById(req.getId()); // 사용자가 존재하는지 확인

        //        UserValidator.checkDuplicatedUsername(
        //                userRepository.existsByUsername(req.getUsername())); // username 중복확인
        //        UserValidator.checkDuplicatedNickname(
        //                userRepository.existsByNickname(req.getNickname())); // nickname 중복확인

        // TODO: 프로필 사진 관련 로직 추가

        user.updateProfile(req);

        return UserServiceMapper.INSTANCE.toUserProfileUpdateRes(user);
    }

    // 비밀번호 수정
    public UserPasswordUpdateRes updatePassword(UserPasswordUpdateReq req) {
        User user = getUserById(req.getId()); // 사용자가 존재하는지 확인

        UserValidator.checkEqualsPassword(
                passwordEncoder.matches(
                        req.getPrePassword(), user.getPassword())); // 저장소 내의 비밀번호랑 req의 prepassword가 일치하는지 확인
        UserValidator.checkEqualsPassword(
                (req.getNewPassword().equals(req.getConfirmPassword()))); // 바꿀 비밀번호와 비밀번호 확인이 같은지 확인

        user.updatePassword(
                passwordEncoder.encode(
                        req.getConfirmPassword())); // 저장소 내의 비밀번호는 암호화 되어있기 때문에 passwordEncoder로 암호화 후 저장

        return new UserPasswordUpdateRes();
    }

    // 아이디 중복확인
    public UsernameVerifyRes verifyUsername(UsernameVerifyReq req) {
        return UsernameVerifyRes.builder()
                .isDuplicated(userRepository.existsByUsername(req.getUsername()))
                .build(); // username 중복확인
    }

    // 닉네임 중복확인
    public NicknameVerifyRes verifyNickname(NicknameVerifyReq req) {
        return NicknameVerifyRes.builder()
                .isDuplicated(userRepository.existsByNickname(req.getNickname()))
                .build(); // nickname 중복확인
    }

    private User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        UserValidator.validate(user);
        return user;
    }
}
