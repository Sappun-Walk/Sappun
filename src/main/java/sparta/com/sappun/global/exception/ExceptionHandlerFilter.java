package sparta.com.sappun.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import sparta.com.sappun.global.response.CommonResponse;
import sparta.com.sappun.global.response.ResultCode;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (GlobalException e) {
            setErrorResponse(response, e.getResultCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ResultCode resultCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(resultCode.getStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(CommonResponse.error(resultCode)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
