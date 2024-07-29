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
}
