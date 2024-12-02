package dreamteam.hitthebook.domain.timer.repository;


import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimerRepository extends JpaRepository<Timer, Long> {
    Optional<Timer> findBySubjectName(String subjectName);

    List<Timer> findByMember(Member member);
}