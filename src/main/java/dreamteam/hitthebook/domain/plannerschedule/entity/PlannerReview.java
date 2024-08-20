package dreamteam.hitthebook.domain.plannerschedule.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

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

    private LocalDateTime reviewAt;

    private String reviewContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
