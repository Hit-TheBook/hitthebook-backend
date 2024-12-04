package dreamteam.hitthebook.domain.member.entity;

import dreamteam.hitthebook.common.commonutil.BaseEntity;
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
@SQLDelete(sql = "UPDATE inventory SET is_deleted = true WHERE inventory_id = ?")
@Getter
@Setter
public class Inventory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emblem_id", nullable = false) // 낫널은 스프링빈 검증, columns방식은 데이터베이스 레벨 검증
    private Emblem emblem; // 이런거 낫널처리 필수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Inventory(Emblem emblem, Member member) {
        this.emblem = emblem;
        this.member = member;
    }

    public static Inventory createInventory(Emblem emblem, Member member){
        return new Inventory(emblem, member);
    }
}
