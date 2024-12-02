package dreamteam.hitthebook.domain.member.repository;

import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmblemRepository extends JpaRepository<Emblem, Long> {
    // 엠블럼 이름으로 검색
    Emblem findByEmblemName(EmblemEnumlation emblemEnumlation);
}
