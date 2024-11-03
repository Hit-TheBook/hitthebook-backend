package dreamteam.hitthebook.domain.timer.service;


import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.helper.TimerHelper;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final TimerHelper timerHelper;

    public TimerContents createTimer(TimerStartRequestDto timerStartRequestDto, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer timer = Timer.createByRequestDto(timerStartRequestDto,member);
        Timer savedTimer = timerRepository.save(timer);
        return new TimerContents(savedTimer);
    }

    public void setStartTimer(TimerPlayRequestDto timerPlayRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer originTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(originTimer,member);
        timerHelper.updateTimerStart(originTimer,timerPlayRequestDto);
    }

    public void setEndTimer(TimerEndRequestDto timerEndRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer originTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(originTimer,member);
        timerHelper.updateTimerEnd(originTimer,timerEndRequestDto);
    }

    public void deleteTimer(Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer originTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(originTimer,member);
        timerHelper.deleteTimerEntity(originTimer);
    }

    public void modifyTimerName(TimerStartRequestDto timerStartRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer originTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(originTimer,member);
        timerHelper.updateTimerName(originTimer,timerStartRequestDto);
    }

    public TimerListDto findTimerList(String emailId, TimerDateDto timerDateDto){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toTimerListDto(member,timerDateDto);
    }

    public TotalTimeDto getTotalTimer(String emailId, TimerDateDto timerDateDto){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.getTotalStudyTime(member,timerDateDto);
    }

    public Map<LocalDate, Duration> getDailyStatisticsForWeek(String emailId, String subjectName, LocalDate date) {
        Member member = timerHelper.findMemberByEmailId(emailId);

        LocalDate sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate saturday = sunday.plusDays(6);

        List<Timer> timers = timerRepository.findWeeklyTimersByMemberAndSubject(
                member, subjectName, sunday.atStartOfDay(), saturday.atTime(LocalTime.MAX));

        Map<LocalDate, Duration> dailyStats = IntStream.range(0, 7)
                .mapToObj(sunday::plusDays)
                .collect(Collectors.toMap(
                        d -> d,
                        d -> Duration.ZERO
                ));

        for (Timer timer : timers) {
            LocalDate timerDate = timer.getStudyStartTime().toLocalDate();
            dailyStats.merge(timerDate, timer.getStudyTimeLength(), Duration::plus);
        }

        return dailyStats;
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void accessTimerDb() {
        // DB 작업 수행
        List<Timer> timers = timerRepository.findAll();
        // 비즈니스 로직 수행
    }
}
