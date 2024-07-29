package dreamteam.hitthebook.domain.dday.repository;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DdayRepository extends JpaRepository<Dday, Long>{

}