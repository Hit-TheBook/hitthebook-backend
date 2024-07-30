package dreamteam.hitthebook.domain.dday.helper;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static dreamteam.hitthebook.domain.dday.dto.DdayDto.*;

@Service
@RequiredArgsConstructor
public class DdayHelper {
    private final MemberRepository memberrepository;
    private final DdayRepository ddayrepository;

    public Member findMemberByEmailId(String emailId){
        return memberrepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public Dday findDdayByDdayId(Long ddayId){
        return ddayrepository.findById(ddayId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }

    public void checkDdayEditPermission(Dday dday, Member member){
        if(!(dday.getMember().equals(member))){throw new RuntimeException();}
    }

    public void updateDdayEntity(Dday dday, DdayRequestDto ddayRequestDto){
        dday.setDdayName(ddayRequestDto.getDdayName());
        dday.setStartDate(ddayRequestDto.getStartDate());
        dday.setEndDate(ddayRequestDto.getEndDate());
        ddayrepository.save(dday);
    }
}
