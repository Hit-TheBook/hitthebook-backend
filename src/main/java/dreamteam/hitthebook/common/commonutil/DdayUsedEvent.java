package dreamteam.hitthebook.common.commonutil;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DdayUsedEvent {
    private final Member member;
    private final Dday dday;
}
