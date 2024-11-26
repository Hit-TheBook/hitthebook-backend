package dreamteam.hitthebook.domain.login.repository;

import dreamteam.hitthebook.domain.login.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailIdAndPassword(String emailId, String password);

    Optional<Member> findByEmailId(String emailId);

    Optional<Member> findByNickname(String nickname);
}
