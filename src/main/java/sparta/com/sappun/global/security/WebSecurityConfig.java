package sparta.com.sappun.global.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static sparta.com.sappun.domain.user.entity.Role.ADMIN;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sparta.com.sappun.global.exception.ExceptionHandlerFilter;
import sparta.com.sappun.global.jwt.JwtAuthorizationFilter;
import sparta.com.sappun.global.jwt.JwtUtil;
import sparta.com.sappun.global.redis.RedisUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, redisUtil, userDetailsService);
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter() {
        return new ExceptionHandlerFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(
                (sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(
                (authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                .permitAll() // resources 접근 허용 설정
                                .requestMatchers("/api/users/signup", "/api/users/login", "/api/users/login-page")
                                .requestMatchers(
                                        "/api/users/signup",
                                        "/api/users/login",
                                        "/api/users/login-page",
                                        "/api/users/signup-page",
                                        "/api/users/username",
                                        "/api/users/nickname")
                                .permitAll() // 회원가입, 로그인 API만 접근 허용
                                .requestMatchers("/api/users/kakao/callback/**")
                                .permitAll()
                                .requestMatchers("/api/users/naver/callback/**")
                                .permitAll()
                                .requestMatchers("/**.html")
                                .permitAll()
                                .requestMatchers("/api/boards/region", "/api/boards/best", "/api/boards/{boardId}")
                                .permitAll() // 게시글 단건 조회, 게시글 목록 조회, 좋아요 Best 3 게시글 목록 조회 접근 허용
                                .requestMatchers(DELETE, "/api/boards/{boardId}/report")
                                .hasAuthority(ADMIN.getAuthority()) // 게시글 신고 삭제는 관리자만 가능
                                .requestMatchers(DELETE, "/api/comments/{commentId}/report")
                                .hasAuthority(ADMIN.getAuthority()) // 댓글 신고 삭제는 관리자만 가능
                                .requestMatchers(GET, "/api/boards/reports")
                                .hasAuthority(ADMIN.getAuthority()) // 신고된 게시글 목록 조회는 관리자만 가능
                                .requestMatchers(GET, "/api/comments/reports")
                                .hasAuthority(ADMIN.getAuthority()) // 신고된 댓글 목록 조회는 관리자만 가능
                                .anyRequest()
                                .authenticated() // 그 외 모든 요청 인증처리
                );

        http.exceptionHandling(
                authenticationManager ->
                        authenticationManager.accessDeniedHandler(customAccessDeniedHandler));

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter(), JwtAuthorizationFilter.class);

        return http.build();
    }
}
