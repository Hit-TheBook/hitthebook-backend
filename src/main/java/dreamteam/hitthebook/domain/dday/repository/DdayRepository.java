package dreamteam.hitthebook.domain.dday.repository;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DdayRepository extends JpaRepository<Dday, Long>{

    // 멤버에 대한 메인 디데이 찾기
    Dday findByMemberAndIsPrimaryTrue(Member member);

    // 멤버에 대한 이미 경과된 디데이 검색
    List<Dday> findByMemberAndEndDateAfter(Member member, LocalDateTime now);

    // 멤버에 대한 현재 활성화된 디데이 검색
    List<Dday> findByMemberAndEndDateBefore(Member member, LocalDateTime now);

    List<Dday> findByMember(Member member);

    @Query("SELECT d FROM Dday d WHERE d.isDeleted = true AND d.updatedAt < :cutoffDate")
    List<Dday> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM Dday d WHERE d.member = :member")
    void deleteByMemberPhysically(@Param("member") Member member);

    @Modifying
    @Query("DELETE FROM Dday d WHERE d.ddayId = :id")
    void deletePhysicallyById(@Param("id") Long ddayId);

}