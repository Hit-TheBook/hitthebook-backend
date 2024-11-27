package dreamteam.hitthebook.domain.timer.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Duration;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE timer SET is_deleted = true WHERE timer_id = ?")
@Getter @Setter
@Table(name = "timer")
public class Timer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_id", nullable = false)
    private Long timerId;

    private LocalDateTime studyTime;

    private String subjectName;

    private Duration totalStudyTimeLength;

    private int totalScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Timer(LocalDateTime studyTime, String subjectName, Duration totalStudyTime, int totalScore, Member member) {
        this.studyTime = studyTime;
        this.subjectName = subjectName;
        this.totalStudyTimeLength = totalStudyTime;
        this.totalScore = totalScore;
        this.member = member;
    }

    public static Timer createByRequestDto(TimerDto.TimerStartRequestDto timerStartRequestDto, Member member) {
        return new Timer(LocalDateTime.now(),timerStartRequestDto.getSubjectName(), Duration.ZERO, 0 , member);
    }
}