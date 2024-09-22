package dreamteam.hitthebook.domain.dday.helper;

import dreamteam.hitthebook.common.exception.ModifyAuthenticationException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.enumulation.DdayTypeEnum;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static dreamteam.hitthebook.domain.dday.dto.DdayDto.*;

@Component
@RequiredArgsConstructor
public class DdayHelper {
    private final MemberRepository memberRepository;
    private final DdayRepository ddayRepository;

    // emailId를 기반으로 멤버 검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    // ddayId를 기반으로 디데이 검색
    public Dday findDdayByDdayId(Long ddayId){
        return ddayRepository.findById(ddayId).orElseThrow(ResourceNotFoundException::new);
    }

    // dday 수정/삭제 권한 검사 후 예외 처리
    public void checkDdayEditPermission(Dday dday, Member member){
        if(!(dday.getMember().equals(member))){throw new ModifyAuthenticationException();}
    }

    // 디데이 엔티티 수정
    public void updateDdayEntity(Dday dday, DdayRequestDto ddayRequestDto){
        dday.setDdayName(ddayRequestDto.getDdayName());
        dday.setStartDate(ddayRequestDto.getStartDate());
        dday.setEndDate(ddayRequestDto.getEndDate());
        ddayRepository.save(dday);
    }

    // 디데이 엔티티 논리 삭제
    public void deleteDdayEntity(Dday dday){
        ddayRepository.delete(dday);
    }

    // 메인 디데이 검색
    public Dday findPrimaryDday(Member member){
        return ddayRepository.findByMemberAndIsPrimaryTrue(member);
    }

    // 현재 메인 디데이가 있다면 일반 디데이로 변환
    public void clearCurrentPrimaryDday(Member member){
        Dday originMainDday = findPrimaryDday(member); // 없으면 실행안해도 되서 옵셔널 사용안함
        if(originMainDday != null){
            originMainDday.setPrimary(false);
            ddayRepository.save(originMainDday);
        }
    }

    // 메인 디데이로 엔티티 수정
    public void updatePrimaryDdayEntity(Member member, Dday dday){
        clearCurrentPrimaryDday(member); // 기존 dday 메인에서 삭제
        dday.setPrimary(true);
    }

    // 메인디데이 이름 검색
    public String getPrimaryDdayName(Dday dday) {
        return Optional.ofNullable(dday).map(Dday::getDdayName).orElse(null); // dday가 null이면 오류 없이 null리턴
    }

    // 메인디데이 남은 날짜 검색
    public Integer getPrimaryDdayRemain(Dday dday) {
        return Optional.ofNullable(dday).map(Dday::getRemainingDays).orElse(null); // dday가 null이면 오류 없이 null 리턴
    }

    public Integer getPrimaryDdayDuration(Dday dday) {
        return Optional.ofNullable(dday).map(Dday::getDurationDays).orElse(null); // dday가 null이면 오류 없이 null 리턴
    }

    // 메인 디데이 종류 판별
    public DdayTypeEnum getPrimaryDdayType(Dday dday) {
        return Optional.ofNullable(dday).map(Dday::getDdayType).orElse(null); // dday가 null이면 오류없이 null 리턴
    }

    // 메인디데이 dto로 변환
    public PrimaryDdayDto toPrimaryDdayDto(Dday dday){
        return new PrimaryDdayDto("Successfully searched primary D-Day", getPrimaryDdayName(dday),
                getPrimaryDdayRemain(dday), getPrimaryDdayDuration(dday), getPrimaryDdayType(dday));
    }

    // 메인 디데이 dto로의 변환
    public DdayContents toPrimaryDdayContents(Member member) {
        return Optional.ofNullable(ddayRepository.findByMemberAndIsPrimaryTrue(member))
                .map(DdayContents::new)
                .orElseGet(DdayContents::new);
    }

    // 활성화된 디데이 리스트 검색
    public List<Dday> findUpcomingDdays(Member member){
        return ddayRepository.findByMemberAndEndDateAfter(member, LocalDate.now().atStartOfDay());
    }

    // 이미 만료된 디데이 리스트 검색
    public List<Dday> findOldDdays(Member member){
        return ddayRepository.findByMemberAndEndDateBefore(member, LocalDate.now().atStartOfDay());
    }

    // 디데이 리스트 dto로 변환
    public DdayListDto toDdayListDto(Member member){
        return new DdayListDto("Successfully searched D-Day list", toPrimaryDdayContents(member), findUpcomingDdays(member), findOldDdays(member));
    }
}
