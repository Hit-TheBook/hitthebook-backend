package dreamteam.hitthebook.domain.timer.helper;

import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.login.repository.MemberRepository;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.util.List;


import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;
@Slf4j
@Component
@RequiredArgsConstructor
public class TimerHelper {

    private final MemberRepository memberRepository;
    private final TimerRepository timerRepository;

    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public Timer findTimerByTimerId(Long timerId){
        return timerRepository.findById(timerId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public void checkTimerEditPermission(Timer timer, Member member){
        if(!(timer.getMember().equals(member))){throw new RuntimeException();}
    }

    public void deleteTimerEntity(Timer timer){timerRepository.delete(timer);
    }

    public void updateTimerStart(Timer timer, TimerPlayRequestDto timerPlayRequestDto){
        timer.setStudyStartTime(timerPlayRequestDto.getTimerStartTime());
        timerRepository.save(timer);
    }

    public void updateTimerEnd(Timer timer, TimerEndRequestDto timerEndRequestDto){
        timer.setStudyTimeLength(timerEndRequestDto.getStudyTimeLength());
        timerRepository.save(timer);
    }

    public void updateTimerName(Timer timer, TimerStartRequestDto timerRequestDto){
        timer.setSubjectName(timerRequestDto.getSubjectName());
        timerRepository.save(timer);
    }

    public TimerListDto toTimerListDto(Member member, TimerDateDto timerDateDto) {
        List<Timer> timerList = timerRepository.findByMemberAndStudyStartTime(member, timerDateDto.getStudyDate());
        return new TimerListDto(timerList);
    }

    public TotalTimeDto getTotalStudyTime(Member member, TimerDateDto timerDateDto) {
        List<Timer> timerList = timerRepository.findByMemberAndStudyStartTime(member, timerDateDto.getStudyDate());

        Duration totalStudyTime = timerList.stream()
                .map(Timer::getStudyTimeLength)
                .reduce(Duration.ZERO, Duration::plus);

        TotalTimeDto totalTimeDto = new TotalTimeDto();
        totalTimeDto.setStudyTimeLength(totalStudyTime);

        return totalTimeDto;
    }

}
