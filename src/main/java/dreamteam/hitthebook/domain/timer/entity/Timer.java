package dreamteam.hitthebook.domain.timer.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.Duration;


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

//    @Column(name = "subject_name", length = 20)
    private String subjectName;

    private Duration studyTimeLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Timer(String subjectName, Duration studyTimeLength, Member member) {
        this.subjectName = subjectName;
        this.studyTimeLength = studyTimeLength;
        this.member = member;
    }

    public static Timer createByRequestDto(TimerDto.TimerStartRequestDto timerStartRequestDto, Member member) {
        return new Timer(timerStartRequestDto.getSubjectName(),Duration.ZERO, member);
    }
}
