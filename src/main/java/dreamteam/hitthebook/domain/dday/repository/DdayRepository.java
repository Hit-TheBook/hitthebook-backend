package dreamteam.hitthebook.domain.dday.repository;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.login.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DdayRepository extends JpaRepository<Dday, Long>{

    Dday findByMemberAndIsPrimaryTrue(Member member);

    List<Dday> findByMemberAndEndDateAfter(Member member, LocalDateTime now);

    List<Dday> findByMemberAndEndDateBefore(Member member, LocalDateTime now);
}