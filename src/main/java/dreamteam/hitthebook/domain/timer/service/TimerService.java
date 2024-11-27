package dreamteam.hitthebook.domain.timer.service;


import dreamteam.hitthebook.common.exception.DuplicateSubjectNameException;
import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.helper.TimerHelper;
import dreamteam.hitthebook.domain.timer.repository.TimerHistoryRepository;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final TimerHelper timerHelper;
    private final TimerHistoryRepository timerHistoryRepository;

    public void createTimer(TimerStartRequestDto timerStartRequestDto, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        if (timerRepository.existsBySubjectName(timerStartRequestDto.getSubjectName())) {
            throw new DuplicateSubjectNameException();
        }
        Timer timer = Timer.createByRequestDto(timerStartRequestDto,member);
        timerRepository.save(timer);
    }

//    public void setStartTimer(TimerPlayRequestDto timerPlayRequestDto, Long timerId, String emailId)
//    {
//        Member member = timerHelper.findMemberByEmailId(emailId);
//        Timer originTimer = timerHelper.findTimerByTimerId(timerId);
//        timerHelper.checkTimerEditPermission(originTimer,member);
//        timerHelper.createTimerHistory(timerPlayRequestDto, originTimer, member);
//    }

    public void setEndTimer(TimerEndRequestDto timerEndRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer timer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(timer,member);
        timerHelper.updateTimerEnd(timer,timerEndRequestDto);
        if(timerEndRequestDto.getScore()!=0)
            timerHelper.createTimerAlert(timer,timerEndRequestDto);
    }

    public void deleteTimer(Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer timer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(timer,member);
        timerHelper.deleteTimerHistoriesByTimerId(timerId);
        timerHelper.deleteTimerEntity(timer);
    }

    public void modifyTimerName(TimerStartRequestDto timerStartRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        if (timerRepository.existsBySubjectName(timerStartRequestDto.getSubjectName())) {
            throw new DuplicateSubjectNameException();
        }
        Timer timer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(timer,member);
        timerHelper.updateTimerName(timer,timerStartRequestDto);
    }

    public TimerListDto findTimerList(String emailId, TimerDateDto timerDateDto) {
        Member member = timerHelper.findMemberByEmailId(emailId);
        LocalDate studyDate = timerDateDto.getStudyDate();
        List<Timer> timerList = timerRepository.findByMemberAndStudyTime(member, studyDate);
        return new TimerListDto(timerList);
    }

    public TotalInfoDto getTotalTimer(String emailId, TimerDateDto timerDateDto){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.getTotalInfo(member,timerDateDto);
    }

    public Map<LocalDate, Duration> getDailyStatisticsForWeek(String emailId, String subjectName, LocalDate date) {
        Member member = timerHelper.findMemberByEmailId(emailId);

        LocalDate sunday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate saturday = sunday.plusDays(6);

        Map<LocalDate, Duration> dailyStats = timerHelper.initializeDateMap(sunday, 7);
        List<Timer> timerHistories = timerHelper.findTimersInRange(member, sunday, saturday);

        timerHelper.addDurationToMap(dailyStats, timerHistories, subjectName, false);

        return dailyStats;
    }

    public Map<LocalDate, Duration> getWeeklyStatisticsForLastFourWeeks(String emailId, String subjectName, LocalDate date) {
        Member member = timerHelper.findMemberByEmailId(emailId);

        LocalDate sundayOfCurrentWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate sundayFourWeeksAgo = sundayOfCurrentWeek.minusWeeks(3);

        Map<LocalDate, Duration> weeklyStats = timerHelper.initializeWeekMap(sundayFourWeeksAgo, 4);
        List<Timer> timerHistories = timerHelper.findTimersInRange(member, sundayFourWeeksAgo, sundayOfCurrentWeek.plusDays(6));

        timerHelper.addDurationToMap(weeklyStats, timerHistories, subjectName, true);

        return weeklyStats;
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void accessTimerDb() {

    }
}