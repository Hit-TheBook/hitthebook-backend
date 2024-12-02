package dreamteam.hitthebook.common.jwt;

import dreamteam.hitthebook.common.exception.InvalidTokenException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try {
            String requestURI = httpRequest.getRequestURI();
            String clientIp = getClientIp(httpRequest);
            log.info("URI : {} / IP : {}", requestURI, clientIp);

            String jwt = jwtTokenHelper.getJwtFromRequest(httpRequest);

            if (StringUtils.hasText(jwt)) {
                if (jwtTokenProvider.validateToken(jwt)) {
                    String emailId = jwtTokenProvider.getEmailIdFromJWT(jwt);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(emailId, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new InvalidTokenException();
                }
            }

            filterChain.doFilter(servletRequest, servletResponse); // 다음 필터로 진행
        } catch (InvalidTokenException ex) {
            log.error("Invalid token exception: {}", ex.getMessage());
            httpResponse.setStatus(499); // HTTP 499 상태 코드 설정
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write("{\"error\": \"Invalid token\"}");
            httpResponse.getWriter().flush();
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // X-Forwarded-For 헤더가 여러 IP를 포함할 수 있으므로 첫 번째 IP만 추출
            return ip.split(",")[0];
        }

        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        return request.getRemoteAddr();
    }
}