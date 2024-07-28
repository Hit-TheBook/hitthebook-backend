package dreamteam.hitthebook.domain.jwt.filter;

import dreamteam.hitthebook.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret; // jwt를 만들 비밀키

    @Value("${jwt.secret2}")
    private String jwtSecret2;

    @Value("${jwt.accessExpiration}")
    private Long accessExpiration; // 엑세스토큰 만료기한

    @Value("${jwt.refreshExpiration}")
    private Long refreshExpiration; // 리프레시토큰 만료기한

    //엑세스 토큰 생성, 토큰생성메소드의 인자에 엑세스토큰 만료기한 대입
    public String generateAccessToken(Member member) {
        return generateToken(member, accessExpiration);
    }

    //리프레시 토큰 생성, 토큰생성메소드의 인자에 리프레시토큰 만료기한 대입
    public String generateRefreshToken(Member member) {
        return generateToken(member, refreshExpiration);
    }

    //토큰 생성 메소드
    private String generateToken(Member member, Long expiration) {
        String subject = member.getEmailId() + "_" + jwtSecret2; // 메인 subject는 유저의 아이디와 다른 문자열을 합쳐서 만든다.
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration); // 만료기한

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    } // HS512와 비밀키를 이용하여 jwt 생성

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken); // 유효한 JWT인지 확인
            return true;
        } catch (Exception e) {
            // throw new 토큰익셉션
        }
        return false;
    }

    public String getEmailIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        String subject = claims.getSubject();
        String[] parts = subject.split("_"); // 닉네임 생성시에, 닉네임 제한 규칙같은 것 정하기
        return parts[0]; // emailId리턴
    }

}