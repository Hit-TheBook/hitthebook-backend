package dreamteam.hitthebook.domain.alert.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.member.entity.Emblem;
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
@SQLDelete(sql = "UPDATE alarm SET is_deleted = true WHERE alert_id = ?")
@Getter @Setter
public class Alert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long alertId;

    @Column(name = "alert_type")
    private AlertTypeEnum alertType;

    private String alertTitle;

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
    @JoinColumn(name = "emblem_id")
    private Emblem emblem;

    private int level;

    public Alert() {

    }

}
