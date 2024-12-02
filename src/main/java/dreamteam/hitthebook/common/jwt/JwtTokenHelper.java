package dreamteam.hitthebook.common.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@AllArgsConstructor
@Component
public class JwtTokenHelper {
    private final JwtTokenProvider jwtTokenProvider;

    public String getMemberEmailIdByToken(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            return jwtTokenProvider.getEmailIdFromJWT(jwt);
        }
        return null; // JWT가 유효하지 않거나 없으면 null 반환
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}