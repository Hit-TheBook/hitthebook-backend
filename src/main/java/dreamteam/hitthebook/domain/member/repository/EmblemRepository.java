package dreamteam.hitthebook.domain.member.repository;

import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmblemRepository extends JpaRepository<Emblem, Long> {
    // 엠블럼 이름으로 검색
    Emblem findByEmblemName(EmblemEnumlation emblemEnumlation);

    @Query("SELECT e FROM Emblem e WHERE e.isDeleted = true AND e.updatedAt < :cutoffDate")
    List<Emblem> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM Emblem e WHERE e.emblemId = :id")
    void deletePhysicallyById(@Param("id") Long emblemId);
}
