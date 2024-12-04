package dreamteam.hitthebook.domain.member.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일아이디와 패스워드로부터 검색 -> 이거는 bcrypt로 인하여 어차피 사용할 일 x
    Optional<Member> findByEmailIdAndPassword(String emailId, String password);

    //이메일아이디로부터 검색
    Optional<Member> findByEmailId(String emailId);

    //닉네임으로부터 검색
    Optional<Member> findByNickname(String nickname);

    @Query("SELECT m FROM Member m WHERE m.isDeleted = true AND m.updatedAt < :cutoffDate")
    List<Member> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM Member m WHERE m.memberId = :id")
    void deleteMemberPhysically(@Param("id") Long memberId);
}
