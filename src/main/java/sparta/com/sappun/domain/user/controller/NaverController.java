package sparta.com.sappun.domain.user.controller;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sparta.com.sappun.domain.user.service.NaverService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/users")
public class NaverController {
    private final NaverService naverService;

    // 네이버 로그인 페이지 가져오기
    @GetMapping("/naver/page")
    public ResponseEntity<String> getNaverLoginPage() {
        return new ResponseEntity<>(naverService.getNaverLoginPage(), HttpStatus.OK);
    }

    // 네이버 로그인
    @GetMapping("/naver/login")
    public String naverLogin(@RequestParam String code, HttpServletResponse res)
            throws JsonProcessingException {
        HashMap<String, String> tokens = naverService.naverLogin(code);

        Cookie cookie = new Cookie(ACCESS_TOKEN_HEADER, tokens.get(ACCESS_TOKEN_HEADER));
        // TODO: 리프레시토큰 저장하는 로직
        cookie.setPath("/");
        res.addCookie(cookie);

        return "redirect:/login.html"; // 로그인 완료시 이동할 페이지
    }
}
