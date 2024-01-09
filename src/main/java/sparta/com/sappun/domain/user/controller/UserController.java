package sparta.com.sappun.domain.user.controller;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static sparta.com.sappun.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;
import static sparta.com.sappun.global.redis.RedisUtil.REFRESH_TOKEN_EXPIRED_TIME;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.com.sappun.domain.user.dto.request.UserLoginReq;
import sparta.com.sappun.domain.user.dto.request.UserSignupReq;
import sparta.com.sappun.domain.user.dto.response.UserLoginRes;
import sparta.com.sappun.domain.user.dto.response.UserLogoutRes;
import sparta.com.sappun.domain.user.dto.response.UserSignupRes;
import sparta.com.sappun.domain.user.service.UserService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;
import sparta.com.sappun.global.response.CommonResponse;

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

        // Refresh token 발급 후 헤더, redis 에 저장
        String refreshToken = jwtUtil.createRefreshToken();
        response.setHeader(REFRESH_TOKEN_HEADER, refreshToken);
        redisUtil.set(
                jwtUtil.getTokenWithoutBearer(refreshToken), res.getId(), REFRESH_TOKEN_EXPIRED_TIME);

        return CommonResponse.success(res);
    }

    @PostMapping("/logout")
    public CommonResponse<UserLogoutRes> logout(HttpServletRequest request) {
        // access token을 헤더에서 가져옴
        String accessToken = jwtUtil.getTokenWithoutBearer(request.getHeader(ACCESS_TOKEN_HEADER));
        String refreshToken = jwtUtil.getTokenWithoutBearer(request.getHeader(REFRESH_TOKEN_HEADER));

        // refresh token이 이미 존재하면 삭제
        if (redisUtil.hasKey(refreshToken)) {
            redisUtil.delete(refreshToken);
        }

        // access token을 블랙리스트에 추가
        redisUtil.set(accessToken, "logout", jwtUtil.getExpiration(accessToken));

        return CommonResponse.success(new UserLogoutRes());
    }
}
