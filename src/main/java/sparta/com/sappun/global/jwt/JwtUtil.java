package sparta.com.sappun.global.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {

    public static final String ACCESS_TOKEN_HEADER = "AccessToken"; // Access Token KEY 값
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken"; // Refresh Token KEY 값
    public static final String AUTHORIZATION_KEY = "auth"; // 사용자 권한 값의 KEY
    public static final String BEARER_PREFIX = "Bearer"; // Token 식별자
    private static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 액세스 토큰 만료시간 60분
    private static final long REFRESH_TOKEN_TIME = 14 * 24 * 60 * 60 * 1000L; // 리프레시 토큰 만료시간 2주

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;
    private JwtParser jwtParser;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct // 1회만 실행됨
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    /** AccessToken 토큰 만들기 */
    public String createAccessToken(Long userId, String role) {
        Date now = new Date();

        return BEARER_PREFIX
                + Jwts.builder()
                        .setSubject(String.valueOf(userId)) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_TIME)) // 만료 기간
                        .setIssuedAt(now)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /** RefreshToken 토큰 만들기 */
    public String createRefreshToken() {
        Date now = new Date();

        return BEARER_PREFIX
                + Jwts.builder()
                        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(now)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /** header 에서 JWT 가져오기 */
    public String getTokenWithoutBearer(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /** 토큰 검증 */
    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    /** 토큰 만료 여부 검증 */
    public boolean isTokenExpired(String token) {
        try {
            jwtParser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    /** 토큰에서 유저 ID 가져오기 */
    public String getUserIdFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }

    /** 액세스 토큰의 남은 유효 시간 확인 */
    public int getExpiration(String accessToken) {
        Date expiration =
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(accessToken)
                        .getBody()
                        .getExpiration();

        long now = new Date().getTime(); // 현재 시간

        return (int) ((expiration.getTime() - now) / (60 * 1000));
    }
}
