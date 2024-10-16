package dreamteam.hitthebook.domain.plannerschedule.repository;

import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PlannerReviewRepository extends JpaRepository<PlannerReview, Long> {
    @Query("SELECT p FROM PlannerReview p WHERE p.member = :member AND " +
            "YEAR(p.reviewAt) = YEAR(:reviewDate) AND " +
            "MONTH(p.reviewAt) = MONTH(:reviewDate) AND " +
            "DAY(p.reviewAt) = DAY(:reviewDate)")
    PlannerReview findByMemberAndReviewAt(@Param("member") Member member, @Param("reviewDate") LocalDateTime reviewDate);
}
