package dreamteam.hitthebook.domain.member.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
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
@SQLDelete(sql = "UPDATE emblem SET is_deleted = true WHERE emblem_id = ?")
@Getter
@Setter
public class Emblem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emblem_id")
    private Long emblemId;

    //    @Column(name = "emblem_name", length = 20)
    private EmblemEnumlation emblemName;

    //    @Column(name = "emblem_content", length = 200)
    private String emblemContent;
}