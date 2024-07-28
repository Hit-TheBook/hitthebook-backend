package dreamteam.hitthebook.domain.mission.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.emblem.entity.Emblem;
import dreamteam.hitthebook.domain.mission.enumulation.MissionTypeEnum;
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
@SQLDelete(sql = "UPDATE mission SET is_deleted = true WHERE mission_id = ?")
@Getter @Setter
public class Mission extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long missionId;

//    @Column(name = "mission_title", nullable = false, length = 50)
    private String missionTitle;

    private Long missionPoint = 0L;

    private int limitedMemberAmount = 10;

    private MissionTypeEnum missionType;

//    @Column(name = "mission_content", length = 300)
    private String missionContent;

    private Integer requiredMinLevel = 0; // 최소 레벨이므로 Integer형태로 처리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "required_emblem_id")
    private Emblem requiredEmblemId; // 해당 엠블럼을 가진 사람들을 모집중이기 때문 객체로
}
