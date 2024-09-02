package dreamteam.hitthebook.domain.plannerschedule.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class PlannerScheduler {
    @Scheduled(fixedRate = 1000)
    public void run() {
        log.info("Scheduler 실행");
    } // 임시 코드 SSE까지 공부후에 로직 생각해보고 작성
}
