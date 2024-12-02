package dreamteam.hitthebook.common.commonutil;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.entity.Inventory;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
import dreamteam.hitthebook.domain.member.repository.EmblemRepository;
import dreamteam.hitthebook.domain.member.repository.InventoryRepository;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerReviewRepository;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class EmblemEventListener {
    private final MemberRepository memberRepository;
    private final InventoryRepository inventoryRepository;
    private final EmblemRepository emblemRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;
    private final PlannerReviewRepository plannerReviewRepository;
    private final TimerRepository timerRepository;
    private final DdayRepository ddayRepository;

    public Emblem getTargetEmblem(EmblemEnumlation emblemEnumlation){
        return emblemRepository.findByEmblemName(emblemEnumlation);
    }

    public boolean hasTargetEmblem(Member member, Emblem emblem){
        return inventoryRepository.findByMemberAndEmblem(member, emblem).isEmpty();
    }

    public void gainTargetEmblem(Member member, Emblem emblem){
        Inventory inventory = Inventory.createInventory(emblem, member);
        inventoryRepository.save(inventory);
    }

    private static final List<ConditionEmblem<?>> EMBLEM_TIMER_SUBJECT_CONDITIONS = List.of(
            new ConditionEmblem<>(Duration.ofMinutes(15), EmblemEnumlation.TIMERSUBJECT15M),
            new ConditionEmblem<>(Duration.ofMinutes(30), EmblemEnumlation.TIMERSUBJECT30M),
            new ConditionEmblem<>(Duration.ofHours(1), EmblemEnumlation.TIMERSUBJECT1H),
            new ConditionEmblem<>(Duration.ofHours(2), EmblemEnumlation.TIMERSUBJECT2H),
            new ConditionEmblem<>(Duration.ofHours(3), EmblemEnumlation.TIMERSUBJECT3H)
    );

    public void handleEmblemLogic(Member member, EmblemEnumlation emblemEnumlation) {
        Emblem emblem = getTargetEmblem(emblemEnumlation);
        if(hasTargetEmblem(member, emblem)){
            gainTargetEmblem(member, emblem);
        }
    }

    public void handleTimerSubjectTotalTimeEmblem(Member member, Timer timer){
        for (ConditionEmblem<?> conditionEmblem : EMBLEM_TIMER_SUBJECT_CONDITIONS) {
            Object condition = conditionEmblem.condition();
            EmblemEnumlation emblemEnumlation = conditionEmblem.emblem();

            if (condition instanceof Duration && timer.getTotalStudyTimeLength().compareTo((Duration) condition) >= 0) {
                handleEmblemLogic(member, emblemEnumlation);
            }
        }
    }

    private static final List<ConditionEmblem<?>> EMBLEM_TIMER_TOTAL_CONDITIONS = List.of(
            new ConditionEmblem<>(1, EmblemEnumlation.TIMERFIRST),
            new ConditionEmblem<>("goal", EmblemEnumlation.TIMERGOALFIRST),
            new ConditionEmblem<>(Duration.ofHours(6), EmblemEnumlation.TIMERTOTAL6H),
            new ConditionEmblem<>(Duration.ofHours(9), EmblemEnumlation.TIMERTOTAL9H),
            new ConditionEmblem<>(Duration.ofHours(12), EmblemEnumlation.TIMERTOTAL12H),
            new ConditionEmblem<>(Duration.ofHours(15), EmblemEnumlation.TIMERTOTAL15H),
            new ConditionEmblem<>(Duration.ofHours(20), EmblemEnumlation.TIMERTOTAL20H),
            new ConditionEmblem<>(Duration.ofHours(30), EmblemEnumlation.TIMERTOTAL30H),
            new ConditionEmblem<>(Duration.ofHours(50), EmblemEnumlation.TIMERTOTAL50H),
            new ConditionEmblem<>(Duration.ofHours(100), EmblemEnumlation.TIMERTOTAL100H),
            new ConditionEmblem<>(Duration.ofHours(200), EmblemEnumlation.TIMERTOTAL200H),
            new ConditionEmblem<>(Duration.ofHours(300), EmblemEnumlation.TIMERTOTAL300H),
            new ConditionEmblem<>(Duration.ofHours(400), EmblemEnumlation.TIMERTOTAL400H)
    );

    public void handleTimerAllTotalTimeEmblem(Member member, TimerHistory timerHistory){
        Duration memberAllStudyTime = member.getAllStudyTime();
        for (ConditionEmblem<?> conditionEmblem : EMBLEM_TIMER_TOTAL_CONDITIONS) {
            Object condition = conditionEmblem.condition();
            EmblemEnumlation emblemEnumlation = conditionEmblem.emblem();

            if (condition instanceof Duration && memberAllStudyTime.compareTo((Duration) condition) >= 0) {
                handleEmblemLogic(member, emblemEnumlation);
            }
            else if(condition instanceof String && timerHistory.getStudyTimeLength().compareTo(timerHistory.getTargetTime()) >= 0){
                handleEmblemLogic(member, emblemEnumlation);
            }
            else if(condition instanceof Integer){
                handleEmblemLogic(member, emblemEnumlation);
            }
        }
    }

    @EventListener
    public void onTimerUsed(TimerUsedEvent event) {
        Member member = event.getMember();
        Timer timer = event.getTimer();
        TimerHistory timerHistory = event.getTimerHistory();

        timerUsedEvent(member, timer, timerHistory);
    }

    public void timerUsedEvent(Member member, Timer timer, TimerHistory timerHistory){
        handleTimerSubjectTotalTimeEmblem(member, timer);
        handleTimerAllTotalTimeEmblem(member, timerHistory);
    }

    public boolean isUsedPlanner(LocalDate targetDate, Member member){
        int year = targetDate.getYear();
        int month = targetDate.getMonthValue();
        int day = targetDate.getDayOfMonth();
        boolean fromSchedule = plannerScheduleRepository.existsByMemberAndCreatedAt(member, year, month, day);
        boolean fromReview = plannerReviewRepository.existsByMemberAndCreatedAt(member, year, month, day);
        return fromSchedule || fromReview;
    }

    public boolean hasSequentialPlanner(Integer day, Member member){
        for(int i = 0; i < day; i++){
            if(!isUsedPlanner(LocalDate.now().minusDays(i), member)){
                return false;
            }
        }
        return true;
    }

    private static final List<ConditionEmblem<?>> EMBLEM_PLANNER_WHOLE_CONDITIONS = List.of(
            new ConditionEmblem<>(2, EmblemEnumlation.PLANNERSEQUENCE2D),
            new ConditionEmblem<>(7, EmblemEnumlation.PLANNERSEQUENCE7D),
            new ConditionEmblem<>(14, EmblemEnumlation.PLANNERSEQUENCE14D),
            new ConditionEmblem<>(28, EmblemEnumlation.PLANNERSEQUENCE28D)
    );

    private static final List<ConditionEmblem<?>> EMBLEM_PLANNER_TYPE_CONDITIONS = List.of(
            new ConditionEmblem<>("subject", EmblemEnumlation.PLANNERSTUDYFIRST),
            new ConditionEmblem<>("schedule", EmblemEnumlation.PLANNERSCHEDULEFIRST)
    );

    public void handlePlannerScheduleEmblem(Member member, PlannerSchedule plannerSchedule){
        for (ConditionEmblem<?> conditionEmblem : EMBLEM_PLANNER_TYPE_CONDITIONS) {
            Object condition = conditionEmblem.condition();
            EmblemEnumlation emblemEnumlation = conditionEmblem.emblem();

            if (condition instanceof String && condition.equals("subject") && plannerSchedule.getScheduleType().equals(ScheduleTypeEnum.SUBJECT)){
                handleEmblemLogic(member, emblemEnumlation);
            }
            else if (condition instanceof String && condition.equals("schedule") && plannerSchedule.getScheduleType().equals(ScheduleTypeEnum.EVENT)){
                handleEmblemLogic(member, emblemEnumlation);
            }
        }
    }

    public void handlePlannerWholeEmblem(Member member){
        for (ConditionEmblem<?> conditionEmblem : EMBLEM_PLANNER_WHOLE_CONDITIONS) {
            Object condition = conditionEmblem.condition();
            EmblemEnumlation emblemEnumlation = conditionEmblem.emblem();

            if (condition instanceof Integer && hasSequentialPlanner((Integer) condition, member)) {
                handleEmblemLogic(member, emblemEnumlation);
            }
        }
    }

    @EventListener
    public void onPlannerUsed(PlannerUsedEvent event) {
        Member member = event.getMember();
        PlannerSchedule plannerSchedule = event.getPlannerSchedule();

        if (plannerSchedule != null) {
            plannerUsedEvent(member, plannerSchedule);
        }

        handlePlannerWholeEmblem(member);
    }

    public void plannerUsedEvent(Member member, PlannerSchedule plannerSchedule){
        handlePlannerScheduleEmblem(member, plannerSchedule);
        handlePlannerWholeEmblem(member);
    }


    private static final List<ConditionEmblem<?>> EMBLEM_DDAY_CONDITIONS = List.of(
            new ConditionEmblem<>("first", EmblemEnumlation.DDAYFIRST)
    );

    public void handleDdayEmblem(Member member, Dday dday){
        for (ConditionEmblem<?> conditionEmblem : EMBLEM_DDAY_CONDITIONS) {
            Object condition = conditionEmblem.condition();
            EmblemEnumlation emblemEnumlation = conditionEmblem.emblem();

            if (condition instanceof String && condition.equals("first")){
                handleEmblemLogic(member, emblemEnumlation);
            }
        }
    }

    @EventListener
    public void onDdayUsed(DdayUsedEvent event) {
        Member member = event.getMember();
        Dday dday = event.getDday();

        DdayUsedEvent(member, dday);
    }

    public void DdayUsedEvent(Member member, Dday dday){
        handleDdayEmblem(member, dday);
    }

}
