package sparta.com.sappun.global.jwt;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static sparta.com.sappun.global.jwt.JwtUtil.BEARER_PREFIX;
import static sparta.com.sappun.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.amazonaws.HttpMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sparta.com.sappun.global.redis.RedisUtil;
import sparta.com.sappun.global.security.UserDetailsImpl;
import sparta.com.sappun.global.security.UserDetailsServiceImpl;
import sparta.com.sappun.global.validator.TokenValidator;

@Slf4j(topic = "JWT validation & authorization")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final List<RequestMatcher> whiteList =
            List.of(
                    new AntPathRequestMatcher("/api/users/signup", HttpMethod.POST.name()),
                    new AntPathRequestMatcher("/api/users/login", HttpMethod.POST.name()),
                    new AntPathRequestMatcher("/api/users/login-page", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/api/users/signup-page", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/api/users/naver/callback/**", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/api/users/kakao/callback/**", HttpMethod.GET.name()),
                    new AntPathRequestMatcher("/**/*.html"));

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Map<String, String> tokens = jwtUtil.getTokensFromCookie(request);
        String accessToken = null;
        String refreshToken = null;

        if (tokens != null) {
            accessToken =
                    jwtUtil.getTokensFromCookie(request).get(ACCESS_TOKEN_HEADER); // access token 찾음
        }
        log.info("accessToken : {}", accessToken);

        // access token 비어있으면 인증 미처리
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // access token 검증
        TokenValidator.checkValidAccessToken(jwtUtil.validateToken(accessToken));

        // 로그아웃된 access token 인지 확인
        TokenValidator.checkLoginRequired(redisUtil.hasKey(accessToken));
        log.info("로그아웃되지 않은 accessToken");

        // 유효한 access token 이면 인증 처리
        if (!jwtUtil.isTokenExpired(accessToken)) {
            setAuthentication(jwtUtil.getUserIdFromToken(accessToken));
            filterChain.doFilter(request, response);
            return;
        }

        log.info("accessToken 만료");

        // access token 이 만료되면 refresh token 가져옴
        refreshToken =
                jwtUtil.getTokensFromCookie(request).get(REFRESH_TOKEN_HEADER); // refresh token 찾음

        log.info("refreshToken : {}", refreshToken);

        // refresh token 검증
        TokenValidator.checkValidRefreshToken(isRefreshTokenValid(refreshToken));

        // refresh token 이 만료되면 로그인 필요 예외 발생
        TokenValidator.checkLoginRequired(jwtUtil.isTokenExpired(refreshToken));

        // 응답 쿠키에 재발급한 access token 반환
        accessToken = renewAccessToken(refreshToken);
        addCookie(accessToken, ACCESS_TOKEN_HEADER, response);
        addCookie(BEARER_PREFIX + refreshToken, REFRESH_TOKEN_HEADER, response);

        log.info("accessToken 재발급 종료");
        log.info("accessToken : {}", accessToken);
        log.info("refreshToken : {}", refreshToken);

        filterChain.doFilter(request, response);
    }

    private String renewAccessToken(String refreshToken) {
        log.info("access token 재발급");
        String userId = redisUtil.get(refreshToken);
        log.info("userId : {}", userId);
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(userId);
        String role = userDetails.getUser().getRole().getAuthority();
        String newAccessToken = jwtUtil.createAccessToken(userId, role); // 새로운 access token 발급

        setAuthentication(userDetails);
        return newAccessToken;
    }

    private boolean isRefreshTokenValid(String refreshToken) {
        return StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken);
    }

    /**
     * 인증 처리
     *
     * @param userId JWT 속의 Subject 에 저장된 (String)userId
     */
    private void setAuthentication(String userId) {
        log.info("set auth userId : {}", userId);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(userId);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
        log.info("setAuthentication 종료");
    }

    /** Access Token을 재발급하는 경우 user를 두 번 조회하지 않도록 추가 */
    private void setAuthentication(UserDetailsImpl userDetails) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken( // createAuthentication 메소드에서 사용자를 다시 조회하지 않도록 바로
                        // userDetails 를 넣음
                        userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    /** 인증 객체 생성 */
    private Authentication createAuthentication(String userId) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /** shouldNotFilter 는 true 를 반환하면 필터링 통과시키는 메서드. */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 현재 URL 이 화이트 리스트에 존재하는지 체크
        return whiteList.stream().anyMatch(whitePath -> whitePath.matches(request));
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
