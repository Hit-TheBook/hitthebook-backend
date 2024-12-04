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
    private final EmblemRepository emblemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public <T> void deleteEntitiesPhysically(List<T> entities) {
        for (T entity : entities) {
            entityManager.remove(entityManager.merge(entity)); // 물리 삭제
        }
    }

    @Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시에 실행
    @Transactional
    public void checkOldDeletedEntity() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(90);

        // 1. Inventory 삭제
        List<Inventory> oldInventories = inventoryRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldInventories);

        // 2. Alert 삭제
        List<Alert> oldAlerts = alertRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldAlerts);

        // 3. Dday 삭제
        List<Dday> oldDdays = ddayRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldDdays);

        // 4. PlannerSchedule 삭제
        List<PlannerSchedule> oldSchedules = plannerScheduleRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldSchedules);

        // 5. PlannerReview 삭제
        List<PlannerReview> oldReviews = plannerReviewRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldReviews);

        // 6. Timer 삭제
        List<Timer> oldTimers = timerRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldTimers);

        // 7. Emblem 삭제
        List<Emblem> oldEmblems = emblemRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldEmblems);

        // 8. Member 삭제
        List<Member> oldMembers = memberRepository.findOldDeletedEntities(cutoffDate);
        deleteEntitiesPhysically(oldMembers);

        log.info("Today physical delete {}", cutoffDate);
    }
}
