package sparta.com.sappun.domain.user.controller;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static sparta.com.sappun.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sparta.com.sappun.domain.user.service.KakaoService;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/users")
public class KakaoController {
    private final KakaoService kakaoService;
    private final RedisUtil redisUtil;
    private final JwtUtil jwtUtil;

    // 카카오 로그인 페이지 가져오기
    @GetMapping("/kakao/page")
    public ResponseEntity<String> getKakaoLoginPage() {
        return new ResponseEntity<>(kakaoService.getKakaoLoginPage(), HttpStatus.OK);
    }

    // 카카오 로그인
    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse res)
            throws JsonProcessingException {
        HashMap<String, String> tokens = kakaoService.kakaoLogin(code);

        addCookie(tokens.get(ACCESS_TOKEN_HEADER), ACCESS_TOKEN_HEADER, res);
        addCookie(tokens.get(REFRESH_TOKEN_HEADER), REFRESH_TOKEN_HEADER, res);

        return "redirect:/api/boards/best"; // 로그인 완료시 이동할 페이지
    }

    private void addCookie(String cookieValue, String header, HttpServletResponse res) {
        Cookie cookie = new Cookie(header, cookieValue); // Name-Value
        cookie.setPath("/");
        cookie.setMaxAge(2 * 60 * 60);
        cookie.setDomain(".sappun.shop");
        //        cookie.setDomain("sappun.shop");
        cookie.setSecure(true); // 쿠키를 안전한 연결에서만 전송
        cookie.setHttpOnly(true); // JavaScript를 통한 액세스 제한

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }
}
