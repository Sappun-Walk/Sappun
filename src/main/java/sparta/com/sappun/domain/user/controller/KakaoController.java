package sparta.com.sappun.domain.user.controller;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sparta.com.sappun.domain.user.service.KakaoService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/users")
public class KakaoController {
    private final KakaoService kakaoService;

    // 카카오 로그인
    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse res)
            throws JsonProcessingException {
        HashMap<String, String> tokens = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(ACCESS_TOKEN_HEADER, tokens.get(ACCESS_TOKEN_HEADER));
        // TODO: 리프레시토큰 저장하는 로직
        cookie.setPath("/");
        res.addCookie(cookie);

        return "redirect:/login.html";
    }
}
