package dreamteam.hitthebook.domain.dday.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.dday.dto.DdayDto;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE dday SET is_deleted = true WHERE dday_id = ?")
@Getter @Setter
public class Dday extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dday_id")
    private Long ddayId;

//    @Column(name = "dday_name", nullable = false, length = 20)
    private String ddayName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean isPrimary = false; // 디폴트는 false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public Dday(String ddayName, LocalDateTime startDate, LocalDateTime endDate, Member member) {
        this.ddayName = ddayName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.member = member;
    }

    public static Dday createByRequestDto(DdayDto.DdayRequestDto ddayRequestDto, Member member) { // 팩토리 메소드
        return new Dday(ddayRequestDto.getDdayName(), ddayRequestDto.getStartDate(), ddayRequestDto.getEndDate(), member);
    }

    public Integer getRemainingDays() { //오늘로부터 디데이 종료날짜의 남은날짜 계산
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), this.endDate.toLocalDate());
    }
}
