package dreamteam.hitthebook.domain.alert.entity;

import dreamteam.hitthebook.common.commonutil.BaseEntity;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "alert_type", nullable = false)
    private AlertTypeEnum alertType;

    @Column(name = "alarm_title", length = 50)
    private String alertTitle;

    @Column(name = "alarm_content", length = 300)
    private String alertContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planner_schedule_id")
    private PlannerSchedule plannerSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emblem_id")
    private Emblem emblem;

    private int level;

    public Alert(Member member){
        this.member = member;
    }

    public Alert(Member member, Emblem emblem){
        this.member = member;
        this.emblem = emblem;
        this.alertType = AlertTypeEnum.EMBLEM;
    }

    public Alert(Member member, int level, String beforeLevel, String afterLevel){
        this.member = member;
        this.level = level;
        this.alertType = AlertTypeEnum.LEVEL;
        this.alertTitle = beforeLevel + "레벨에서" + afterLevel + "레벨로 승급하였습니다.";
    }


}
