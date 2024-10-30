package dreamteam.hitthebook.domain.alert.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.alert.enumulation.TargetPageTypeEnum;
import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
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
@SQLDelete(sql = "UPDATE alert SET is_deleted = true WHERE alert_id = ?")
@Getter @Setter
public class Alert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long alertId;

//    @Column(name = "alarm_uri", length = 500)
//    private String alarmUri;

    @Column(name = "target_page")
    private TargetPageTypeEnum targetPage;

//    @Column(name = "alarm_content", length = 200)
    private String alertContent;

    private boolean isChecked = false;

    private LocalDateTime alertTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_schedule_id")
    private PlannerSchedule plannerSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

//    public Alert(TargetPageTypeEnum targetPage, String alertContent, Member member, PlannerSchedule plannerSchedule) {
//        this.targetPage = targetPage;
//        this.alertContent = alertContent;
//        this.alertTime = plannerSchedule.getScheduleAt().minusDays(1);
//        this.member = member;
//        this.plannerSchedule = plannerSchedule;
//    }

    public Alert(TargetPageTypeEnum targetPage,String alertContent,Assignment assignment, Member member) {
        this.targetPage = targetPage;
        this.alertContent = alertContent;
        this.assignment = assignment;
        this.member = member;
    }

}
