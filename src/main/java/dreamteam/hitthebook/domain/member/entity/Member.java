package dreamteam.hitthebook.domain.member.entity;

import dreamteam.hitthebook.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE Member SET is_deleted = true WHERE member_id = ?")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String emailId;

    private String password;

    @Column(name = "nickname", nullable = false, unique = true) //length = 20
    private String nickname;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private String kakaoId;

    private LocalDateTime lastLoginedAt;

    @Column(name = "point")
    private Long point = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emblem_id")
    private Emblem emblem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

}
