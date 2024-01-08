package sparta.com.sappun.global.jwt;

import static sparta.com.sappun.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;

import com.amazonaws.HttpMethod;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
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
            List.of(new AntPathRequestMatcher("/api/users/signup/**", HttpMethod.POST.name()));
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken =
                jwtUtil.getTokenWithoutBearer(request.getHeader(ACCESS_TOKEN_HEADER)); // access token 찾음
        log.info("accessToken : {}", accessToken);

        // access token 비어있으면 인증 미처리
        if (!StringUtils.hasText(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 우선 access token 으로 검증
        TokenValidator.checkValidToken(jwtUtil.validateToken(accessToken));

        // TODO: 로그아웃된 access token 인지 확인

        // 유효한 access token 이면 인증 처리
        if (!jwtUtil.isTokenExpired(accessToken)) {
            setAuthentication(jwtUtil.getUserIdFromToken(accessToken));
            filterChain.doFilter(request, response);
            return;
        }

        // access token 이 만료되면 refresh token 검증
        String refreshToken = findRefreshTokenByAccessToken(accessToken);
        TokenValidator.checkValidToken(isRefreshTokenValid(refreshToken));

        // refresh token 이 만료되면 로그인 필요 예외 발생
        TokenValidator.checkLoginRequired(jwtUtil.isTokenExpired(refreshToken));

        // 응답 헤더에 재발급한 access token 반환
        response.addHeader(ACCESS_TOKEN_HEADER, renewAccessToken(accessToken));
        filterChain.doFilter(request, response);
    }

    private String findRefreshTokenByAccessToken(String accessToken) {
        log.info("refresh token 검색");
        String userId = jwtUtil.getUserIdFromToken(accessToken);
        return (String) redisUtil.get(userId);
    }

    private String renewAccessToken(String accessToken) {
        log.info("access token 재발급");
        String userId = jwtUtil.getUserIdFromToken(accessToken); // access token 으로 userId를 찾음
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserById(userId);
        String role = userDetails.getUser().getRole().getAuthority();
        String newAccessToken = jwtUtil.createAccessToken(userId, role); // 새로운 access token 발급

        setAuthentication(userId);
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
    }

    /** 인증 객체 생성 */
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    /** shouldNotFilter 는 true 를 반환하면 필터링 통과시키는 메서드. */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 현재 URL 이 화이트 리스트에 존재하는지 체크
        return whiteList.stream().anyMatch(whitePath -> whitePath.matches(request));
    }
}
