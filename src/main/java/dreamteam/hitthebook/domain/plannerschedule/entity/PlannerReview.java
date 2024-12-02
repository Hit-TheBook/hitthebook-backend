package dreamteam.hitthebook.domain.plannerschedule.entity;

import dreamteam.hitthebook.common.commonutil.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE planner_review SET is_deleted = true WHERE planner_review_id = ?")
@Getter @Setter
@Table(name = "planner_review")
public class PlannerReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planner_review_id")
    private Long plannerReviewId;

    @Column(nullable = false)
    private LocalDateTime reviewAt;

    @Column(length = 1000)
    private String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Column(nullable = false)
    private Member member;

    public PlannerReview(LocalDateTime reviewAt, Member member) {
        this.reviewAt = reviewAt;
        this.member = member;
    }

    public static PlannerReview createByRequestDto(LocalDateTime reviewAt, Member member){
        return new PlannerReview(reviewAt, member);
    }
}
