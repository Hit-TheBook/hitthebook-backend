package dreamteam.hitthebook.common.commonutil;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.entity.Inventory;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.EmblemRepository;
import dreamteam.hitthebook.domain.member.repository.InventoryRepository;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerReview;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerReviewRepository;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.repository.TimerHistoryRepository;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class PhysicalDeleteManeger {
    private final MemberRepository memberRepository;
    private final InventoryRepository inventoryRepository;
    private final AlertRepository alertRepository;
    private final DdayRepository ddayRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;
    private final PlannerReviewRepository plannerReviewRepository;
    private final TimerRepository timerRepository;
    private final TimerHistoryRepository timerHistoryRepository;
    private final EmblemRepository emblemRepository;

    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 실행
    @Transactional
    public void checkOldDeletedEntity() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(90);

        // 1. Inventory 삭제
        List<Inventory> oldInventories = inventoryRepository.findOldDeletedEntities(cutoffDate);
        for (Inventory inventory : oldInventories) {
            inventoryRepository.deletePhysicallyById(inventory.getInventoryId());
        }

        // 2. Alert 삭제
        List<Alert> oldAlerts = alertRepository.findOldDeletedEntities(cutoffDate);
        for (Alert alert : oldAlerts) {
            alertRepository.deletePhysicallyById(alert.getAlertId());
        }

        // 3. Dday 삭제
        List<Dday> oldDdays = ddayRepository.findOldDeletedEntities(cutoffDate);
        for (Dday dday : oldDdays) {
            ddayRepository.deletePhysicallyById(dday.getDdayId());
        }

        // 4. PlannerSchedule 삭제
        List<PlannerSchedule> oldSchedules = plannerScheduleRepository.findOldDeletedEntities(cutoffDate);
        for (PlannerSchedule plannerSchedule : oldSchedules) {
            plannerScheduleRepository.deletePhysicallyById(plannerSchedule.getPlannerScheduleId());
        }

        // 5. PlannerReview 삭제
        List<PlannerReview> oldReviews = plannerReviewRepository.findOldDeletedEntities(cutoffDate);
        for (PlannerReview plannerReview : oldReviews) {
            plannerReviewRepository.deletePhysicallyById(plannerReview.getPlannerReviewId());
        }

        // 6. Timer 삭제
        List<Timer> oldTimers = timerRepository.findOldDeletedEntities(cutoffDate);
        for (Timer timer : oldTimers) {
            timerHistoryRepository.deleteByTimerPhysically(timer);
            timerRepository.deletePhysicallyById(timer.getTimerId());
        }

        // 7. Emblem 삭제
        List<Emblem> oldEmblems = emblemRepository.findOldDeletedEntities(cutoffDate);
        for (Emblem emblem : oldEmblems) {
            emblemRepository.deletePhysicallyById(emblem.getEmblemId());
        }

        log.info("Today physical delete {}", cutoffDate);
    }
}
