package dreamteam.hitthebook.domain.dday.service;

import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.helper.DdayHelper;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dreamteam.hitthebook.domain.dday.dto.DdayDto.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DdayService {
    private final DdayHelper ddayHelper;
    private final DdayRepository ddayRepository;

    // 디데이 생성
    public void createDday(DdayRequestDto ddayRequestDto, String emailId){
        ddayHelper.checkInvalidDday(ddayRequestDto);
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday dday = Dday.createByRequestDto(ddayRequestDto, member);
        ddayRepository.save(dday);
    }

    // 디데이 수정
    public void modifyDday(DdayRequestDto ddayRequestDto, String emailId, Long ddayId){
        ddayHelper.checkInvalidDday(ddayRequestDto);
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 수정자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.updateDdayEntity(originDday, ddayRequestDto); // dday업데이트 처리
    }

    // 디데이 삭제
    public void deleteDday(String emailId, Long ddayId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 삭제 요청자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.deleteDdayEntity(originDday);
    }

    // 메인 디데이 설정
    public void setPrimaryDday(String emailId, Long ddayId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 수정자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.updatePrimaryDdayEntity(member, originDday);
    }

    // 메인 디데이 검색
    public PrimaryDdayDto findPrimaryDday(String emailId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday primaryDday = ddayHelper.findPrimaryDday(member);
        return ddayHelper.toPrimaryDdayDto(primaryDday);
    }

    // 디데이 목록 검색
    public DdayListDto findDdayList(String emailId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        return ddayHelper.toDdayListDto(member);
    }
}
