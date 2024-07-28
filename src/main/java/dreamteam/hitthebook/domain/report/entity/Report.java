package dreamteam.hitthebook.domain.report.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.mission.entity.Mission;
import dreamteam.hitthebook.domain.missionchat.entity.MissionChat;
import dreamteam.hitthebook.domain.missionpost.entity.MissionPost;
import dreamteam.hitthebook.domain.report.enumulation.ReportReasonEnum;
import dreamteam.hitthebook.domain.report.enumulation.ReportStatusEnum;
import dreamteam.hitthebook.domain.report.enumulation.ReportTypeEnum;
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
@SQLDelete(sql = "UPDATE report SET is_deleted = true WHERE report_id = ?")
@Getter @Setter
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_member_id")
    private Member reportMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_member_id")
    private Member targetMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_mission_id")
    private Mission targetMission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_chat_id")
    private MissionChat targetChat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_post_id")
    private MissionPost targetPost;

    @Enumerated(EnumType.STRING)
    private ReportTypeEnum reportType;

    @Enumerated(EnumType.STRING)
    private ReportReasonEnum reportReason;

//    @Column(name = "report_content", nullable = false, length = 1000)
    private String reportContent;

    @Enumerated(EnumType.STRING)
    private ReportStatusEnum reportStatus;
}
