package dreamteam.hitthebook.domain.timer.helper;

import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.TargetPageTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.login.repository.MemberRepository;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.repository.TimerHistoryRepository;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimerHelper {

    private final MemberRepository memberRepository;
    private final TimerRepository timerRepository;
    private final TimerHistoryRepository timerHistoryRepository;
    private final AlertRepository alertRepository;

    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public Timer findTimerByTimerId(Long timerId){
        return timerRepository.findById(timerId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public void createTimerAlert(Timer timer, TimerEndRequestDto timerEndRequestDto) {

        Member member = timer.getMember();
        Alert alert = new Alert();

        Duration targetTime = timerEndRequestDto.getTargetTime();
        Duration studyTimeLength = timerEndRequestDto.getStudyTimeLength();
        Duration exceededTime = studyTimeLength.minus(targetTime.isNegative() ? Duration.ZERO : targetTime);
        String alertContent = String.format(
                "%s 과목을 목표시간보다 %s분을 더 공부해 %d점을 획득하셨습니다!",
                timer.getSubjectName(),
                (int) exceededTime.toMinutes(),
                timerEndRequestDto.getScore()
        );

        alert.setTargetPage(TargetPageTypeEnum.TIMER);
        alert.setAlertContent(alertContent);
        alert.setAlertTime(LocalDateTime.now());
        alert.setChecked(false);
        alert.setMember(member);
        alert.setTimer(timer);
        alertRepository.save(alert);
    }

    public void checkTimerEditPermission(Timer timer, Member member){
        if(!(timer.getMember().equals(member))){throw new RuntimeException();}
    }

    public void deleteTimerHistoriesByTimerId(Long timerId) {
        timerHistoryRepository.deleteByTimer_TimerId(timerId);
    }

    public void deleteTimerEntity(Timer timer) {
        timerRepository.delete(timer);
    }

    public void createTimerHistory(Timer timer , TimerEndRequestDto timerEndRequestDto) {

        TimerHistory timerHistory = new TimerHistory();
        timerHistory.setStudyTime(timer.getStudyTime());
        timerHistory.setTargetTime(timerEndRequestDto.getTargetTime());
        timerHistory.setMember(timer.getMember());
        timerHistory.setTimer(timer);
        timerHistory.setStudyTimeLength(timerEndRequestDto.getStudyTimeLength());
        timerHistory.setScore(timerEndRequestDto.getScore());
        timerHistoryRepository.save(timerHistory);
    }

    public void updateTimerEnd(Timer timer, TimerEndRequestDto timerEndRequestDto) {

        timer.setStudyTime(LocalDateTime.now());
        timer.setTotalStudyTimeLength(timerEndRequestDto.getStudyTimeLength());
        timer.setTotalScore(timerEndRequestDto.getScore());
        timerRepository.save(timer);
        createTimerHistory(timer, timerEndRequestDto);
    }

    public int calculateAndUpdateScore(TimerHistory timerHistory, TimerEndRequestDto timerEndRequestDto) {

        Duration studyTimeLength = timerEndRequestDto.getStudyTimeLength();
        Duration targetTime = timerHistory.getTargetTime();

        int targetMinutes = (int) targetTime.toMinutes();
        int studyMinutes = (int) studyTimeLength.toMinutes();
        int score = 0;

        if (studyMinutes >= targetMinutes) {
            score = targetMinutes;
            int extraTime = studyMinutes - targetMinutes;
            int extraPeriods = extraTime / 10;
            score += (extraPeriods * 20);
        }

        timerHistory.setScore(score);
        timerHistoryRepository.save(timerHistory);

        return score;
    }

    public void updateTimerName(Timer timer, TimerStartRequestDto timerRequestDto) {
        timer.setSubjectName(timerRequestDto.getSubjectName());
        timerRepository.save(timer);
    }

    public TotalInfoDto getTotalInfo(Member member, TimerDateDto timerDateDto) {

        LocalDate studyDate = timerDateDto.getStudyDate();

        List<Timer> timerList = timerRepository.findByMemberAndStudyTimeDate(member, studyDate);

        Duration totalStudyTime = timerList.stream()
                .map(Timer::getTotalStudyTimeLength)
                .reduce(Duration.ZERO, Duration::plus);

        int totalScore = timerList.stream()
                .mapToInt(Timer::getTotalScore)
                .sum();

        TotalInfoDto totalInfoDto = new TotalInfoDto();
        totalInfoDto.setStudyTimeLength(totalStudyTime);
        totalInfoDto.setScore(totalScore);

        return totalInfoDto;
    }

    public Map<LocalDate, Duration> initializeDateMap(LocalDate startDate, int days) {
        Map<LocalDate, Duration> dateMap = new TreeMap<>();
        for (int i = 0; i < days; i++) {
            dateMap.put(startDate.plusDays(i), Duration.ZERO);
        }
        return dateMap;
    }

    public Map<LocalDate, Duration> initializeWeekMap(LocalDate startDate, int weeks) {
        return IntStream.range(0, weeks)
                .mapToObj(startDate::plusWeeks)
                .collect(Collectors.toMap(Function.identity(), weekStart -> Duration.ZERO, (a, b) -> a, TreeMap::new));
    }

    public List<Timer> findTimersInRange(Member member, LocalDate startDate, LocalDate endDate) {
        return timerRepository.findByMemberAndStudyTimeBetween(
                member, startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }

    public void addDurationToMap(Map<LocalDate, Duration> statsMap, List<Timer> timers, String subjectName, boolean isWeekly) {
        for (Timer timer : timers) {
            if (subjectName == null || timer.getSubjectName().equals(subjectName)) {
                LocalDate timerDate = timer.getStudyTime().toLocalDate();
                LocalDate periodStart = isWeekly ? timerDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)) : timerDate;
                statsMap.merge(periodStart, timer.getTotalStudyTimeLength(), Duration::plus);
            }
        }
    }

}