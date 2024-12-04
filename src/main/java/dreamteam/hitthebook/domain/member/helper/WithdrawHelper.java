package dreamteam.hitthebook.domain.member.helper;

import dreamteam.hitthebook.common.commonutil.Level;
import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawHelper {
    private final MemberRepository memberRepository;
    private final InventoryRepository inventoryRepository;
    private final AlertRepository alertRepository;
    private final DdayRepository ddayRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;
    private final PlannerReviewRepository plannerReviewRepository;
    private final TimerRepository timerRepository;
    private final TimerHistoryRepository timerHistoryRepository;

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public <T> void deleteEntitiesPhysically(List<T> entities) {
//        for (T entity : entities) {
//            entityManager.remove(entityManager.merge(entity)); // 물리 삭제
//        }
//    }
//
//    public void deleteInventoryOfMember(Member member){
//        List<Inventory> inventory = inventoryRepository.findByMember(member);
//        deleteEntitiesPhysically(inventory);
//    }
//
//    public void deleteAlertOfMember(Member member){
//        List<Alert> alert = alertRepository.findByMember(member);
//        deleteEntitiesPhysically(alert);
//    }
//
//    public void deleteDdayOfMember(Member member){
//        List<Dday> dday = ddayRepository.findByMember(member);
//        deleteEntitiesPhysically(dday);
//    }
//
//    public void deletePlannerScheduleOfMember(Member member){
//        List<PlannerSchedule> plannerSchedule = plannerScheduleRepository.findByMember(member);
//        deleteEntitiesPhysically(plannerSchedule);
//    }
//
//    public void deletePlannerReviewOfMember(Member member){
//        List<PlannerReview> plannerReview = plannerReviewRepository.findByMember(member);
//        deleteEntitiesPhysically(plannerReview);
//    }
//
//    public void deleteTimerOfMember(Member member){
//        List<Timer> timer = timerRepository.findByMember(member);
//        deleteEntitiesPhysically(timer);
//    }
//
//    public void physicalDeleteMemberOfMember(Member member){
//        Member managedMember = entityManager.merge(member); // 영속성 컨테스트에서 관리되지 않는 엔티티로 변환
//        entityManager.remove(managedMember); // 물리 삭제
//    }
//
//    public void removeMemberWithRelations(Member member){
//        deleteInventoryOfMember(member);
//        deleteAlertOfMember(member);
//        deleteDdayOfMember(member);
//        deletePlannerScheduleOfMember(member);
//        deletePlannerReviewOfMember(member);
//        deleteTimerOfMember(member);
//        physicalDeleteMemberOfMember(member);
//    }


    public void deleteInventoryOfMember(Member member) {
        inventoryRepository.deleteByMemberPhysically(member);
    }

    public void deleteAlertOfMember(Member member) {
        alertRepository.deleteByMemberPhysically(member);
    }

    public void deleteDdayOfMember(Member member) {
        ddayRepository.deleteByMemberPhysically(member);
    }

    public void deletePlannerScheduleOfMember(Member member) {
        plannerScheduleRepository.deleteByMemberPhysically(member);
    }

    public void deletePlannerReviewOfMember(Member member) {
        plannerReviewRepository.deleteByMemberPhysically(member);
    }

    public void deleteTimerOfMember(Member member) {
        deleteTimerHistoryOfTimer(timerRepository.findByMember(member));
        timerRepository.deleteByMemberPhysically(member);
    }

    public void deleteTimerHistoryOfTimer(List<Timer> timerList) {
        for(Timer timer : timerList) {
            timerHistoryRepository.deleteByTimerPhysically(timer);
        }
    }

    public void deleteMember(Member member) {
        memberRepository.deleteMemberPhysically(member.getMemberId());
    }

    public void removeMemberWithRelations(Member member) {
        deleteInventoryOfMember(member);
        deleteAlertOfMember(member);
        deleteDdayOfMember(member);
        deletePlannerScheduleOfMember(member);
        deletePlannerReviewOfMember(member);
        deleteTimerOfMember(member);
        deleteMember(member);
    }

}
