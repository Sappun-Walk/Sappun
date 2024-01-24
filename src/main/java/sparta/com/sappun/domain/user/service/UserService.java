package sparta.com.sappun.domain.user.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sparta.com.sappun.domain.board.repository.BoardRepository;
import sparta.com.sappun.domain.comment.repository.CommentRepository;
import sparta.com.sappun.domain.likeBoard.entity.LikeBoard;
import sparta.com.sappun.domain.likeBoard.repository.LikeBoardRepository;
import sparta.com.sappun.domain.likeComment.entity.LikeComment;
import sparta.com.sappun.domain.likeComment.repository.LikeCommentRepository;
import sparta.com.sappun.domain.reportBoard.entity.ReportBoard;
import sparta.com.sappun.domain.reportBoard.repository.ReportBoardRepository;
import sparta.com.sappun.domain.reportComment.entity.ReportComment;
import sparta.com.sappun.domain.reportComment.repository.ReportCommentRepository;
import sparta.com.sappun.domain.user.dto.request.NicknameVerifyReq;
import sparta.com.sappun.domain.user.dto.request.UserLoginReq;
import sparta.com.sappun.domain.user.dto.request.UserPasswordUpdateReq;
import sparta.com.sappun.domain.user.dto.request.UserProfileUpdateReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.request.UsernameVerifyReq;
import sparta.com.sappun.domain.user.dto.response.NicknameVerifyRes;
import sparta.com.sappun.domain.user.dto.response.UserDeleteRes;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserPasswordUpdateRes;
import sparta.com.sappun.domain.user.dto.response.UserProfileRes;
import sparta.com.sappun.domain.user.dto.response.UserProfileUpdateRes;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.dto.response.UsernameVerifyRes;
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
    private final ReportBoardRepository reportBoardRepository;
    private final ReportCommentRepository reportCommentRepository;
    private final LikeBoardRepository likeBoardRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Util s3Util;

    @Value("${default.image.url}")
    private String defaultProfileImage;

    private static final String EMPTY_FILE_TITLE = "empty.txt";
    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer LIKE_POINT = 10;
    private static final Integer REPORT_POINT = 50;

    @Transactional
    public UserSignupRes signup(UserSignupReq req, MultipartFile multipartFile) {
        UserValidator.validate(req);

        UserValidator.checkDuplicatedUsername(
                userRepository.existsByUsername(req.getUsername())); // username 중복확인
        UserValidator.checkDuplicatedNickname(
                userRepository.existsByNickname(req.getNickname())); // nickname 중복확인

        UserValidator.checkEmail(userRepository.existsByEmail(req.getEmail()));

        if (Objects.equals(multipartFile.getOriginalFilename(), EMPTY_FILE_TITLE)) multipartFile = null;

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
                        .score(DEFAULT_SCORE)
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

        // 사용자가 신고한 게시글 및 댓글의 작성자 점수 +50 후 신고 삭제
        List<ReportBoard> reportBoards = reportBoardRepository.selectReportBoardByUser(user);
        for (ReportBoard reportBoard : reportBoards) {
            User reportedUser = reportBoard.getBoard().getUser();
            reportedUser.updateScore(REPORT_POINT);
        }
        reportBoardRepository.deleteAll(reportBoards);

        List<ReportComment> reportComments = reportCommentRepository.selectReportCommentByUser(user);
        for (ReportComment reportComment : reportComments) {
            User reportedUser = reportComment.getComment().getUser();
            reportedUser.updateScore(REPORT_POINT);
        }
        reportCommentRepository.deleteAll(reportComments);

        // 사용자가 좋아요를 누른 게시글 및 댓글의 작성자 점수 -10 후 좋아요 삭제
        List<LikeBoard> likeBoards = likeBoardRepository.selectLikeBoardByUser(user);
        for (LikeBoard likeBoard : likeBoards) {
            User reportedUser = likeBoard.getBoard().getUser();
            reportedUser.updateScore(-LIKE_POINT);
        }
        likeBoardRepository.deleteAll(likeBoards);

        List<LikeComment> likeComments = likeCommentRepository.selectLikeCommentByUser(user);
        for (LikeComment likeComment : likeComments) {
            User reportedUser = likeComment.getComment().getUser();
            reportedUser.updateScore(-LIKE_POINT);
        }
        likeCommentRepository.deleteAll(likeComments);

        // 사용자가 작성한 댓글 삭제
        commentRepository.deleteAllByUser(user);

        // 사용자가 작성한 게시글 삭제
        boardRepository.deleteAllByUser(user);

        // 사용자 삭제
        String imageUrl = user.getProfileUrl(); // 기존 프로필 이미지
        if (!imageUrl.equals(defaultProfileImage)) { // 기존 이미지가 기본 프로필이 아닌 경우
            s3Util.deleteFile(imageUrl, FilePath.PROFILE); // 기존 이미지 삭제
        }
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
        UserValidator.validate(req);
        if (Objects.equals(multipartFile.getOriginalFilename(), EMPTY_FILE_TITLE)) multipartFile = null;

        // 프로필 사진 관련 로직
        String imageUrl = user.getProfileUrl(); // 기존 프로필 이미지
        if (multipartFile != null && !multipartFile.isEmpty()) { // 새로 입력한 이미지 파일이 있는 경우
            if (!imageUrl.equals(defaultProfileImage)) { // 기존 이미지가 기본 프로필이 아닌 경우
                s3Util.deleteFile(imageUrl, FilePath.PROFILE); // 기존 이미지 삭제
            }
            S3Validator.isProfileImageFile(multipartFile); // 이미지 파일인지 확인
            imageUrl = s3Util.uploadFile(multipartFile, FilePath.PROFILE); // 업로드 후 프로필로 설정
        }

        user.updateProfile(req, imageUrl); // 프로필 업데이트

        return UserServiceMapper.INSTANCE.toUserProfileUpdateRes(user);
    }

    // 비밀번호 수정
    @Transactional
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
