package dreamteam.hitthebook.domain.timer.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;
import dreamteam.hitthebook.domain.timer.enumulation.TimerColorTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.cglib.core.Local;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE timer SET is_deleted = true WHERE timer_id = ?")
@Getter @Setter
public class Timer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id", nullable = false)
    private Long timerId;

    private String subjectName;

    private Duration studyTimeLength;

    private LocalDateTime studyStartTime;

    private LocalDateTime targetTime;

    @Enumerated(EnumType.STRING)
    private TimerColorTypeEnum timerColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Timer(String subjectName, LocalDateTime targetTime, TimerColorTypeEnum timerColor, Duration studyTimeLength, Member member) {
        this.subjectName = subjectName;
        this.targetTime = targetTime;
        this.timerColor = timerColor;
        this.studyTimeLength = studyTimeLength;
        this.member = member;
    }

    public static Timer createByRequestDto(TimerDto.TimerStartRequestDto timerStartRequestDto, Member member) {
        LocalTime time = LocalTime.parse(timerStartRequestDto.getTimerTargetTime()); // 변환 로직
        LocalDateTime targetTime = LocalDateTime.of(LocalDate.now(), time); // 오늘 날짜와 결합
        return new Timer(timerStartRequestDto.getSubjectName(), targetTime, timerStartRequestDto.getTimerColorType(),Duration.ZERO, member);
    }
}
