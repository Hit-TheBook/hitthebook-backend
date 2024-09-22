package dreamteam.hitthebook.common.jwt;

import dreamteam.hitthebook.common.exception.InvalidTokenException;
import dreamteam.hitthebook.configuration.PathsConfig;
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
import java.util.Arrays;

@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter implements Filter {
    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestURI = httpRequest.getRequestURI();
        log.info("URI : {}", requestURI);

        String jwt = jwtTokenHelper.getJwtFromRequest(httpRequest);

        // JWT가 존재하고 유효한지 검증
        if (StringUtils.hasText(jwt)) {
            if (jwtTokenProvider.validateToken(jwt)) {
                // 토큰이 유효한 경우
                String emailId = jwtTokenProvider.getEmailIdFromJWT(jwt);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(emailId, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // 토큰이 유효하지 않으면 InvalidTokenException 발생
                throw new InvalidTokenException();
            }
        }

        // 필터 체인 계속 처리
        filterChain.doFilter(servletRequest, servletResponse);
    }
}