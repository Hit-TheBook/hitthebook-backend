package dreamteam.hitthebook.domain.plannerschedule.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.dday.dto.DdayDto;
import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.plannerschedule.dto.PlannerDto;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
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
@SQLDelete(sql = "UPDATE planner_schedule SET is_deleted = true WHERE planner_schedule_id = ?")
@Getter @Setter
@Table(name = "planner_schedule")
public class PlannerSchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planner_schedule_id")
    private Long plannerScheduleId;

    private LocalDateTime scheduleAt;

    private LocalDateTime startAt;
    private LocalDateTime endAt;

//    @Column(name = "schedule_title", nullable = false, length = 50)
    private String scheduleTitle;

//    @Column(name = "schedule_content", length = 300)
    private String scheduleContent;

    @Enumerated(EnumType.STRING)
    private ScheduleTypeEnum scheduleType;

    @Enumerated(EnumType.STRING)
    private FeedbackTypeEnum scheduleFeedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "before_schedule_id")
    private PlannerSchedule beforeSchedule;

    public PlannerSchedule(LocalDateTime scheduleAt, String scheduleTitle, String scheduleContent, ScheduleTypeEnum scheduleType, Member member,
                           LocalDateTime startAt, LocalDateTime endAt) {
        this.scheduleAt = scheduleAt;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleType = scheduleType;
        this.member = member;
    }

    public PlannerSchedule(LocalDateTime scheduleAt, String scheduleTitle, String scheduleContent, ScheduleTypeEnum scheduleType, Member member,
                           LocalDateTime startAt, LocalDateTime endAt, PlannerSchedule beforeSchedule) {
        this.scheduleAt = scheduleAt;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleType = scheduleType;
        this.member = member;
        this.beforeSchedule = beforeSchedule;
    }

    public static PlannerSchedule createByRequestDto(PlannerDto.ScheduleRequestDto scheduleRequestDto, ScheduleTypeEnum scheduleType, Member member){
        return new PlannerSchedule(scheduleRequestDto.getScheduleDate(), scheduleRequestDto.getScheduleTitle(),
                scheduleRequestDto.getContent(), scheduleType, member, scheduleRequestDto.getStartDate(), scheduleRequestDto.getEndDate());
    }

    public static PlannerSchedule createNewPostponeEntity(PlannerDto.PostPoneDto postPoneDto, PlannerSchedule originalPlannerSchedule){
        return new PlannerSchedule(postPoneDto.getScheduleDate(), originalPlannerSchedule.scheduleTitle, originalPlannerSchedule.scheduleContent,
                originalPlannerSchedule.scheduleType, originalPlannerSchedule.member, postPoneDto.getStartDate(),
                postPoneDto.getEndDate(), originalPlannerSchedule.beforeSchedule);
    }

}
