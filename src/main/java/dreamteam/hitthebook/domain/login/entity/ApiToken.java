package dreamteam.hitthebook.domain.login.entity;


import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.entity.Member;
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
@SQLDelete(sql = "UPDATE Api_Token SET is_deleted = true WHERE token_id = ?")
@Getter
@Setter
public class ApiToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public ApiToken(String refreshToken, Member member) {
        this.refreshToken = refreshToken;
        this.member = member;
    }
}
