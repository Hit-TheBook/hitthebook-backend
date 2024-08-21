package dreamteam.hitthebook.domain.timer.service;


import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.helper.TimerHelper;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
