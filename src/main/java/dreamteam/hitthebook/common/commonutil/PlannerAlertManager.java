package dreamteam.hitthebook.common.commonutil;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class PlannerAlertManager {
    private final AlertRepository alertRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;

//    @Scheduled(cron = "0 26 22 * * *") // 22시 26분
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    public void checkAndCreateAlerts() {
        LocalDateTime startOfTomorrow = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfTomorrow = LocalDate.now().plusDays(1).atTime(23, 59, 59);

        List<PlannerSchedule> plannerSchedules = plannerScheduleRepository.findByScheduleAtBetween(startOfTomorrow, endOfTomorrow);

        for (PlannerSchedule plannerSchedule : plannerSchedules) {
            Alert alert = new Alert(plannerSchedule.getMember());
            alert.setAlertType(AlertTypeEnum.PLANNER);
            alert.setAlertTitle("<" + plannerSchedule.getScheduleTitle() + "> 일정이 내일 예정되어 있습니다.");
            alertRepository.save(alert);
        }
    }
}
