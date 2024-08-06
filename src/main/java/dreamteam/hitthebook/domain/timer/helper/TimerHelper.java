package dreamteam.hitthebook.domain.timer.helper;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.timer.dto.TimerDto;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;
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

    public void updateTimerTime(Timer timer, TimerRequestDto timerRequestDto){
        timer.setStudyTimeLength(timerRequestDto.getStudyTimeLength());
        timerRepository.save(timer);
    }

    public void updateTimerName(Timer timer, TimerRequestDto timerRequestDto){
        timer.setSubjectName(timerRequestDto.getSubjectName());
        timerRepository.save(timer);
    }
}
