package dreamteam.hitthebook.common.aop;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PlannerAspect {
//    private final AlertRepository alertRepository;
//
//    @Pointcut("execution(* dreamteam.hitthebook.domain.plannerschedule.helper.PlannerHelper.createNewPlannerScheduleEvent(..))")
//    public void createNewPlannerScheduleEventPointcut() {}
//
//    @After("createNewPlannerScheduleEventPointcut()")
//    public void scheduleAlertAspect() {
//        Alert alert = new Alert();
//        alertRepository.save(alert);
//    }
    // 이런형태로는 aop를 구현할 이유가 없음!!!!!!!!!!!!!!
    // 중요한 포인트 효율적인 코드관리를 위한 것이 aop의 목적 중 하나
    // 복잡해지는 부분은 개별 메소드로 처리하고, 일부 중복처리되는 코드들을 aop에 넣는 것이 좋음
}