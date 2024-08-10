package dreamteam.hitthebook.domain.login.repository;

import dreamteam.hitthebook.domain.login.entity.ApiToken;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<ApiToken, Long> {

    Optional<ApiToken> findByRefreshToken(String refreshToken);

    ApiToken findByMemberAndCreatedAtAfter(Member member, LocalDateTime date);

    Optional<ApiToken> findByRefreshTokenAndCreatedAtAfter(String refreshToken, LocalDateTime date);

    ApiToken findByMember(Member member);
}
