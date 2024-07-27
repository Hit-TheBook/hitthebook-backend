package dreamteam.hitthebook.domain.level.entity;

import dreamteam.hitthebook.common.entity.BaseEntity;
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
@SQLDelete(sql = "UPDATE level SET is_deleted = true WHERE level_id = ?")
@Getter @Setter
public class Level extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_id")
    private Long levelId;

//    @Column(name = "level_name", nullable = false, length = 20)
    private String levelName;

    private Long currentLevelPoint;

    private Long nextLevelPoint;
}
