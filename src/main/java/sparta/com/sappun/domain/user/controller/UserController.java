package sparta.com.sappun.domain.user.controller;

import static sparta.com.sappun.global.redis.RedisUtil.REFRESH_TOKEN_EXPIRED_TIME;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.com.sappun.domain.user.dto.request.UserLoginReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserProfileRes;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.security.UserDetailsImpl;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @PostMapping("/signup")
    public CommonResponse<UserSignupRes> signup(
            @RequestBody @Valid UserSignupReq req) { // TODO: 프로필 사진 입력받기
        return CommonResponse.success(userService.signup(req));
    }

    @PostMapping("/login")
    public CommonResponse<UserLoginRes> login(
            @RequestBody UserLoginReq req, HttpServletResponse response) {
        UserLoginRes res = userService.login(req);

        // Access token 발급 후 헤더에 저장
        String accessToken = jwtUtil.createAccessToken(res.getId(), res.getRole().getValue());
        response.setHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);

        // Refresh token 존재하면 삭제
        if (redisUtil.hasKey(String.valueOf(res.getId()))) {
            redisUtil.delete(String.valueOf(res.getId()));
        }

        // Refresh token 발급 후 헤더, redis 에 저장
        String refreshToken = jwtUtil.createRefreshToken();
        response.setHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
        redisUtil.set(
                jwtUtil.getTokenWithoutBearer(refreshToken), res.getId(), REFRESH_TOKEN_EXPIRED_TIME);

        return CommonResponse.success(res);
    }

    // 프로필 조회
    @GetMapping("")
    public CommonResponse<UserProfileRes> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return CommonResponse.success(userService.getProfile(userDetails.getUser().getId()));
    }

}
