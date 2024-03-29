package sparta.com.sappun.domain.user.controller;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static sparta.com.sappun.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;
import static sparta.com.sappun.global.redis.RedisUtil.REFRESH_TOKEN_EXPIRED_TIME;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sparta.com.sappun.domain.user.dto.request.*;
import sparta.com.sappun.domain.user.dto.response.*;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    private static final String LOGOUT_VALUE = "logout";

    @GetMapping("/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup-page")
    public String signupPage() {
        return "signup";
    }

    @ResponseBody
    @PostMapping("/signup")
    public CommonResponse<UserSignupRes> signup(@Valid UserSignupReq req) {
        return CommonResponse.success(userService.signup(req, req.getImage()));
    }

    @ResponseBody
    @PostMapping("/login")
    public CommonResponse<UserLoginRes> login(
            @RequestBody UserLoginReq req, HttpServletResponse response) {
        UserLoginRes res = userService.login(req);

        // Access token 발급 후 쿠키에 저장
        String accessToken =
                jwtUtil.createAccessToken(String.valueOf(res.getId()), res.getRole().getValue());
        addCookie(accessToken, ACCESS_TOKEN_HEADER, response);

        // Refresh token 발급 후 쿠키, redis 에 저장
        String refreshToken = jwtUtil.createRefreshToken();
        addCookie(refreshToken, REFRESH_TOKEN_HEADER, response);
        redisUtil.set(
                jwtUtil.getTokenWithoutBearer(refreshToken),
                String.valueOf(res.getId()),
                REFRESH_TOKEN_EXPIRED_TIME);

        return CommonResponse.success(res);
    }

    @ResponseBody
    @PostMapping("/logout")
    public CommonResponse<UserLogoutRes> logout(HttpServletRequest request) {
        // access token을 헤더에서 가져옴
        Map<String, String> tokens = jwtUtil.getTokensFromCookie(request);
        String accessToken = null;
        String refreshToken = null;

        if (tokens != null) {
            accessToken = jwtUtil.getTokensFromCookie(request).get(ACCESS_TOKEN_HEADER);
            refreshToken = jwtUtil.getTokensFromCookie(request).get(REFRESH_TOKEN_HEADER);
        }

        // refresh token이 이미 존재하면 삭제
        if (redisUtil.hasKey(refreshToken)) {
            redisUtil.delete(refreshToken);
        }

        // access token을 블랙리스트에 추가
        redisUtil.set(accessToken, LOGOUT_VALUE, jwtUtil.getExpiration(accessToken));

        return CommonResponse.success(new UserLogoutRes());
    }

    @ResponseBody
    @DeleteMapping
    public CommonResponse<UserDeleteRes> deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(userService.deleteUser(userDetails.getUser().getId()));
    }

    // 프로필 조회 페이지
    @GetMapping
    public String getProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserProfileRes res = userService.getProfile(userDetails.getUser().getId());

        model.addAttribute("userProfile", res);

        return "profile";
    }

    // 프로필 조회
    @ResponseBody
    @GetMapping("/profile/{userId}")
    public CommonResponse<UserProfileRes> getProfileInfo(@PathVariable Long userId) {
        return CommonResponse.success(userService.getProfileInfo(userId));
    }

    // 프로필 수정 페이지
    @GetMapping("/profile-update")
    public String getProfileForInput(
            Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserProfileRes res = userService.getProfile(userDetails.getUser().getId());

        model.addAttribute("userProfile", res);

        return "profileUpdate";
    }

    // 프로필 수정
    @ResponseBody
    @PatchMapping("/profile")
    public CommonResponse<UserProfileUpdateRes> updateProfile(
            @Valid UserProfileUpdateReq req, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setId(userDetails.getUser().getId());
        UserProfileUpdateRes res = userService.updateProfile(req, req.getImage());
        return CommonResponse.success(res);
    }

    // 비밀번호 수정 페이지
    @GetMapping("/profile/password")
    public String updatePasswordPage() {
        return "pwUpdate";
    }

    // 비밀번호 수정
    @ResponseBody
    @PatchMapping("/profile/password")
    public CommonResponse<UserPasswordUpdateRes> updatePassword(
            @RequestBody @Valid UserPasswordUpdateReq req,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        req.setId(userDetails.getUser().getId());
        return CommonResponse.success(userService.updatePassword(req));
    }

    // 아이디 중복 확인
    @ResponseBody
    @PostMapping("/username")
    public CommonResponse<UsernameVerifyRes> verifyUsername(
            @RequestBody @Valid UsernameVerifyReq req) {
        UsernameVerifyRes res = userService.verifyUsername(req);
        return CommonResponse.success(res);
    }

    // 닉네임 중복확인
    @ResponseBody
    @PostMapping("/nickname")
    public CommonResponse<NicknameVerifyRes> verifyNickname(
            @RequestBody @Valid NicknameVerifyReq req) {
        NicknameVerifyRes res = userService.verifyNickname(req);
        return CommonResponse.success(res);
    }

    private void addCookie(String cookieValue, String header, HttpServletResponse res) {
        Cookie cookie = new Cookie(header, cookieValue); // Name-Value
        cookie.setPath("/");
        cookie.setMaxAge(2 * 60 * 60);
        //        cookie.setDomain(".sappun.shop");
        //        cookie.setSecure(true); // 쿠키를 안전한 연결에서만 전송
        //        cookie.setHttpOnly(true); // JavaScript를 통한 액세스 제한

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }
}
