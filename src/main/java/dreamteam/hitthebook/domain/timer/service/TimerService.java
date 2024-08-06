package dreamteam.hitthebook.domain.timer.service;


import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;
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

    public void createTimer(TimerRequestDto timerRequestDto, String emailId){
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer timer = Timer.createByRequestDto(timerRequestDto,member);
        timerRepository.save(timer);
    }

    public void setTimer(TimerRequestDto timerRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer startTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(startTimer,member);
        timerHelper.updateTimerTime(startTimer,timerRequestDto);
    }

    public void deleteTimer(Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer originTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(originTimer,member);
        timerHelper.deleteTimerEntity(originTimer);
    }

    public void modifyTimerName(TimerRequestDto timerRequestDto, Long timerId, String emailId)
    {
        Member member = timerHelper.findMemberByEmailId(emailId);
        Timer startTimer = timerHelper.findTimerByTimerId(timerId);
        timerHelper.checkTimerEditPermission(startTimer,member);
        timerHelper.updateTimerName(startTimer,timerRequestDto);
    }
}
