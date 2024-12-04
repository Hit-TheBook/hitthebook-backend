package dreamteam.hitthebook.domain.plannerschedule.repository;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PlannerReviewRepository extends JpaRepository<PlannerReview, Long> {
    // 리뷰날짜에 해당하는 멤버의 플래너리뷰 검색
    @Query("SELECT p FROM PlannerReview p WHERE p.member = :member AND " +
            "YEAR(p.reviewAt) = YEAR(:reviewDate) AND " +
            "MONTH(p.reviewAt) = MONTH(:reviewDate) AND " +
            "DAY(p.reviewAt) = DAY(:reviewDate)")
    PlannerReview findByMemberAndReviewAt(@Param("member") Member member, @Param("reviewDate") LocalDateTime reviewDate);

    // 해당날짜에 멤버가 생성한 플래너 리뷰가 있는지 검색
    @Query("SELECT COUNT(pr) > 0 FROM PlannerReview pr WHERE pr.member = :member AND " +
            "YEAR(pr.createdAt) = :year AND MONTH(pr.createdAt) = :month AND DAY(pr.createdAt) = :day")
    boolean existsByMemberAndCreatedAt(@Param("member") Member member,
                                       @Param("year") int year,
                                       @Param("month") int month,
                                       @Param("day") int day);

    List<PlannerReview> findByMember(Member member);

    @Query("SELECT r FROM PlannerReview r WHERE r.isDeleted = true AND r.updatedAt < :cutoffDate")
    List<PlannerReview> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM PlannerReview pr WHERE pr.member = :member")
    void deleteByMemberPhysically(@Param("member") Member member);

    @Modifying
    @Query("DELETE FROM PlannerReview pr WHERE pr.plannerReviewId = :id")
    void deletePhysicallyById(@Param("id") Long plannerReviewId);
}
