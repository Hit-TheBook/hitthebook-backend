package dreamteam.hitthebook.domain.dday.helper;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static dreamteam.hitthebook.domain.dday.dto.DdayDto.*;

@Component
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

    public void deleteDdayEntity(Dday dday){
        ddayrepository.delete(dday);
    }

    public Dday findPrimaryDday(Member member){
        return ddayrepository.findByMemberAndIsPrimaryTrue(member);
    }

    public void clearCurrentPrimaryDday(Member member){
        Dday originMainDday = findPrimaryDday(member); // 없으면 실행안해도 되서 옵셔널 사용안함
        if(originMainDday != null){
            originMainDday.setPrimary(false);
            ddayrepository.save(originMainDday);
        }
    }

    public void updatePrimaryDdayEntity(Member member, Dday dday){
        clearCurrentPrimaryDday(member); // 기존 dday 메인에서 삭제
        dday.setPrimary(true);
    }

    public String getPrimaryDdayName(Dday dday) {
        return Optional.ofNullable(dday).map(Dday::getDdayName).orElse(null); // dday가 null이면 오류 없이 null리턴
    }

    public Integer getPrimaryDdayRemain(Dday dday) {
        return Optional.ofNullable(dday).map(Dday::getRemainingDays).orElse(null); // dday가 null이면 오류 없이 null 리턴
    }

    public PrimaryDdayDto toPrimaryDdayDto(Dday dday){
        return new PrimaryDdayDto("successful", getPrimaryDdayName(dday), getPrimaryDdayRemain(dday));
    }
}
