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

    public void createDday(DdayRequestDto ddayRequestDto, String emailId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday dday = Dday.createByRequestDto(ddayRequestDto, member);
        ddayRepository.save(dday);
    }

    public void modifyDday(DdayRequestDto ddayRequestDto, String emailId, Long ddayId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 수정자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.updateDdayEntity(originDday, ddayRequestDto); // dday업데이트 처리
    }

    public void deleteDday(String emailId, Long ddayId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 삭제 요청자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.deleteDdayEntity(originDday);
    }

    public void setPrimaryDday(String emailId, Long ddayId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 수정자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.updatePrimaryDdayEntity(member, originDday);
    }

    public PrimaryDdayDto findPrimaryDday(String emailId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday primaryDday = ddayHelper.findPrimaryDday(member);
        return ddayHelper.toPrimaryDdayDto(primaryDday);
    }
}
