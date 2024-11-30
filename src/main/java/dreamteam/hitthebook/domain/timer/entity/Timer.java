package dreamteam.hitthebook.domain.timer.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;

import dreamteam.hitthebook.common.commonutil.DurationConverter;
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

    private String subjectName;

    @Convert(converter = DurationConverter.class)
    private Duration totalStudyTimeLength = Duration.ZERO;

    private int totalScore = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Timer(String subjectName, Member member) {
        this.subjectName = subjectName;
        this.member = member;
    }

    public static Timer createBySubjectName(String subjectName, Member member) {
        return new Timer(subjectName, member);
    }
}