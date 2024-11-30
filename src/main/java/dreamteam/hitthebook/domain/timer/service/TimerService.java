package dreamteam.hitthebook.domain.timer.service;


import dreamteam.hitthebook.common.commonutil.TimerUsedEvent;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.helper.TimerHelper;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;
    private final TimerHelper timerHelper;
    private final ApplicationEventPublisher eventPublisher;

    public void createTimer(String subjectName, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        timerHelper.checkSameSubjectName(subjectName);
        Timer timer = Timer.createBySubjectName(subjectName, member);
        timerRepository.save(timer);
    }

    public void updateTimer(TimerHistoryRequestDto timerHistoryRequestDto, Long timerId, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer timer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(timer,member);
        timerHelper.updateMemberTimerData(timerHistoryRequestDto, member);
        timerHelper.updateTimerData(timer, timerHistoryRequestDto);
        TimerHistory timerHistory = timerHelper.createTimerHistory(timer, timerHistoryRequestDto, member);
        eventPublisher.publishEvent(new TimerUsedEvent(member, timer, timerHistory));
    }

    public void deleteTimer(Long timerId, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer timer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(timer,member);
        timerHelper.deleteTimerHistoryByTimerId(timer);
        timerHelper.deleteTimerEntity(timer);
    }

    public void modifyTimerName(String subjectName, Long timerId, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        timerHelper.checkSameSubjectName(subjectName);
        Timer timer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(timer,member);
        timerHelper.updateTimerName(timer, subjectName);
    }

    public TimerListDto findTimerList(String emailId) {
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toTimerListDto(member);
    }

    public TodayTimerDataDto getTodayTimerData(String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toTodayTimerDataDto(member);
    }

    public TargetDateDailyStatistics getTotalDailyStatistics(String emailId, LocalDate targetDate){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toTotalTargetDateDailyStatistics(member, targetDate);
    }

    public TargetDateDailyStatistics getSubjectDailyStatistics(String emailId, LocalDate targetDate, String subjectName){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toSubjectTargetDateDailyStatistics(member, targetDate, subjectName);
    }

    public TargetDateWeeklyStatistics getTotalWeeklyStatistics(String emailId, LocalDate targetDate){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toTotalTargetDateWeeklyStatistics(member, targetDate);
    }

    public TargetDateWeeklyStatistics getSubjectWeeklyStatistics(String emailId, LocalDate targetDate, String subjectName){
        Member member = timerHelper.findMemberByEmailId(emailId);
        return timerHelper.toSubjectTargetDateWeeklyStatistics(member, targetDate, subjectName);
    }









    @Scheduled(cron = "0 0 5 * * ?")
    public void accessTimerDb() {

    }
}