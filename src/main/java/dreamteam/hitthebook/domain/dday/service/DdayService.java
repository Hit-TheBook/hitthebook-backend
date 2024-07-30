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
@RequiredArgsConstructor
public class DdayService {
    private final DdayHelper ddayHelper;
    private final DdayRepository ddayRepository;

    @Transactional
    public void createDday(DdayRequestDto ddayRequestDto, String emailId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday dday = Dday.createByRequestDto(ddayRequestDto, member);
        ddayRepository.save(dday);
    }

    @Transactional
    public void modifyDday(DdayRequestDto ddayRequestDto, String emailId, Long ddayId){
        Member member = ddayHelper.findMemberByEmailId(emailId);
        Dday originDday = ddayHelper.findDdayByDdayId(ddayId);
        ddayHelper.checkDdayEditPermission(originDday, member); // 수정자가 작성자 본인인지 확인후 다르면 예외처리
        ddayHelper.updateDdayEntity(originDday, ddayRequestDto); // dday업데이트 처리
    }


}
