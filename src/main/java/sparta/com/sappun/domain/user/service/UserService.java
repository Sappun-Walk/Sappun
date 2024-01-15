package sparta.com.sappun.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.user.dto.request.*;
import sparta.com.sappun.domain.user.dto.response.*;
import sparta.com.sappun.domain.user.dto.response.UserProfileUpdateRes;
import sparta.com.sappun.domain.user.entity.Role;
import sparta.com.sappun.domain.user.entity.User;
import sparta.com.sappun.domain.user.entity.UserSocialEnum;
import sparta.com.sappun.domain.user.repository.UserRepository;
import sparta.com.sappun.global.validator.S3Validator;
import sparta.com.sappun.global.validator.UserValidator;
import sparta.com.sappun.infra.s3.S3Util;
import sparta.com.sappun.infra.s3.S3Util.FilePath;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Util s3Util;

    @Value("${default.image.url}")
    private String defaultProfileImage;

    @Transactional
    public UserSignupRes signup(UserSignupReq req, MultipartFile multipartFile) {
        UserValidator.validate(req);

        UserValidator.checkEmail(userRepository.existsByEmail(req.getEmail()));

        String profileImage = defaultProfileImage; // 기본 프로필 이미지 설정
        if (multipartFile != null && !multipartFile.isEmpty()) { // 이미지 입력이 있는 경우
            S3Validator.isProfileImageFile(multipartFile); // 이미지 파일인지 확인
            profileImage = s3Util.uploadFile(multipartFile, FilePath.PROFILE); // 이미지 업로드 후 프로필로 설정
        }

        userRepository.save(
                User.builder()
                        .username(req.getUsername())
                        .nickname(req.getNickname())
                        .email(req.getEmail())
                        .password(passwordEncoder.encode(req.getPassword()))
                        .profileUrl(profileImage)
                        .role(Role.USER)
                        .score(0)
                        .social(UserSocialEnum.LOCAL)
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
    public UserProfileUpdateRes updateProfile(UserProfileUpdateReq req, MultipartFile multipartFile) {
        User user = getUserById(req.getId()); // 사용자가 존재하는지 확인

        //        UserValidator.checkDuplicatedUsername(
        //            userRepository.existsByUsername(req.getUsername())); // username 중복확인
        //        UserValidator.checkDuplicatedNickname(
        //            userRepository.existsByNickname(req.getNickname())); // nickname 중복확인

        // 프로필 사진 관련 로직
        String imageUrl = user.getProfileUrl(); // 기존 프로필 이미지
        if (!imageUrl.equals(defaultProfileImage)) { // 기존 이미지가 기본 프로필이 아닌 경우
            s3Util.deleteFile(imageUrl, FilePath.PROFILE); // 기존 이미지 삭제
        }
        if (multipartFile == null || multipartFile.isEmpty()) { // 새로 입력한 이미지 파일이 없는 경우
            imageUrl = defaultProfileImage; // 기본 이미지로
        } else {
            S3Validator.isProfileImageFile(multipartFile); // 이미지 파일인지 확인
            imageUrl = s3Util.uploadFile(multipartFile, FilePath.PROFILE); // 업로드 후 프로필로 설정
        }

        user.updateProfile(req, imageUrl); // 프로필 업데이트

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
