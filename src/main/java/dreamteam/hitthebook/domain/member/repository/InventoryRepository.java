package dreamteam.hitthebook.domain.member.repository;

import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.entity.Inventory;
import dreamteam.hitthebook.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // 멤버가 가진 엠블럼 검색
    List<Inventory> findByMember(Member member);

    // 멤버와 엠블럼으로부터 검색
    Optional<Inventory> findByMemberAndEmblem(Member member, Emblem emblem);

    @Query("SELECT i FROM Inventory i WHERE i.isDeleted = true AND i.updatedAt < :cutoffDate")
    List<Inventory> findOldDeletedEntities(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.member = :member")
    void deleteByMemberPhysically(@Param("member") Member member);

    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.inventoryId = :id")
    void deletePhysicallyById(@Param("id") Long inventoryId);
}
