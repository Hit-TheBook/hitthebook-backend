package dreamteam.hitthebook.domain.member.entity;

import dreamteam.hitthebook.common.commonutil.DurationConverter;
import dreamteam.hitthebook.common.commonutil.BaseEntity;
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
@SQLDelete(sql = "UPDATE Member SET is_deleted = true WHERE member_id = ?")
@Getter @Setter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "email_id", unique = true, nullable = false)
    private String emailId;

    @Column(nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true, length = 20)
    private String nickname;

    // 처리 로직을 어떻게 할지 모르겠음..., 해당 멤버가 api를 쏠 때마다 필터에서 잡을 수 있으면 잡아서 갱신해야할거같은데 가능한가?
    private LocalDateTime lastLoginedAt;

    @Column(name = "point", nullable = false)
    private int point = 0;

    @Column(name = "level", nullable = false)
    private int level = 1;

    @Convert(converter = DurationConverter.class)
    @Column(nullable = false)
    private Duration allStudyTime = Duration.ZERO;

    public Member (String emailId, String paassword, String nickname){
        this.emailId = emailId;
        this.password = paassword;
        this.nickname = nickname;
    }

//    public static Member createByRequestDto(LoginDto.JoinRequestDto joinRequestDto){
//        return new Member(joinRequestDto.emailId, joinRequestDto.password, joinRequestDto.nickname);
//    }

}
