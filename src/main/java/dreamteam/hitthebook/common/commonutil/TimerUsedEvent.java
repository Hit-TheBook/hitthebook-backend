package dreamteam.hitthebook.common.commonutil;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimerUsedEvent {
    private final Member member;
    private final Timer timer;
    private final TimerHistory timerHistory;
}