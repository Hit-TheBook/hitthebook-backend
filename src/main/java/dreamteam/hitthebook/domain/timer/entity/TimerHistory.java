package dreamteam.hitthebook.domain.timer.entity;

import dreamteam.hitthebook.common.commonutil.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;
import dreamteam.hitthebook.common.commonutil.DurationConverter;
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
@SQLDelete(sql = "UPDATE timer_history SET is_deleted = true WHERE timer_history_id = ?")
@Getter @Setter
@Table(name = "timer_history")
public class TimerHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timer_history_id", nullable = false)
    private Long timerHistoryId;

    private int score;

    @Convert(converter = DurationConverter.class)
    @Column(nullable = false)
    private Duration studyTimeLength;

    private LocalDateTime studyTime;

    @Convert(converter = DurationConverter.class)
    @Column(nullable = false)
    private Duration targetTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timer_id")
    private Timer timer;

    public TimerHistory(Member member, Timer timer, int score, Duration studyTimeLength, Duration targetTime) {
        this.member = member;
        this.timer = timer;
        this.score = score;
        this.studyTimeLength = studyTimeLength;
        this.studyTime = LocalDateTime.now();
        this.targetTime = targetTime;
    }

    // 팩토리 메소드
    public static TimerHistory createByTimerHistoryRequestDto(Timer timer, TimerDto.TimerHistoryRequestDto timerHistoryRequestDto, Member member) {
        return new TimerHistory(member, timer, timerHistoryRequestDto.getScore(), timerHistoryRequestDto.getStudyTimeLengthAsDuration(), timerHistoryRequestDto.getTargetTimeAsDuration());
    }
}