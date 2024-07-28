package dreamteam.hitthebook.domain.missionpost.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.mission.entity.Mission;
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
@SQLDelete(sql = "UPDATE mission_post SET is_deleted = true WHERE mission_post_id = ?")
@Getter @Setter
public class MissionPost extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_post_id")
    private Long missionPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

//    @Column(name = "post_title", nullable = false, length = 20)
    private String postTitle;

//    @Column(name = "post_content", nullable = false, length = 1000)
    private String postContent;

    private Integer postLike = 0; // 임시로 들어간 필드, 기획에 따라서 없어질 수 있음

    private Integer postUnlike = 0; // 임시로 들어간 필드, 기획에 따라서 없어질 수 있음
}
