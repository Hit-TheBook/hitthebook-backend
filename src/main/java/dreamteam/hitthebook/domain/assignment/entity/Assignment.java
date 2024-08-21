package dreamteam.hitthebook.domain.assignment.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.assignment.dto.AssignmentDto;
import dreamteam.hitthebook.domain.assignment.enumulation.AssignmentDayOfWeekEnum;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE assignment SET is_deleted = true WHERE assignment_id = ?")
@Getter @Setter
public class Assignment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @ElementCollection(targetClass = AssignmentDayOfWeekEnum.class)
    @Enumerated(EnumType.STRING)
    private List<AssignmentDayOfWeekEnum> dayOfWeekEnum;

    private String assignmentContent;

    private LocalDate assignmentStartAt;

    private LocalDate assignmentEndAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Assignment(List<AssignmentDayOfWeekEnum> dayOfWeekEnum, String assignmentContent, LocalDate assignmentStartAt, LocalDate assignmentEndAt, Member member){
        this.dayOfWeekEnum = dayOfWeekEnum;
        this.assignmentContent = assignmentContent;
        this.assignmentStartAt = assignmentStartAt;
        this.assignmentEndAt = assignmentEndAt;
        this.member = member;
    }

    public static Assignment createByRequestDto(AssignmentDto.AssignmentRequestDto assignmentRequestDto, Member member){
        return new Assignment(assignmentRequestDto.getDayOfWeekEnumList(),
                assignmentRequestDto.getAssignmentContent(),
                assignmentRequestDto.getAssignmentStartAt(),
                assignmentRequestDto.getAssignmentEndAt(),
                member);
    }
}
