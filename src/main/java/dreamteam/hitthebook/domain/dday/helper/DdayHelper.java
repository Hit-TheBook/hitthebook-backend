package dreamteam.hitthebook.domain.dday.helper;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DdayHelper {
    private final MemberRepository memberrepository;

    public Member findMemberByEmailId(String emailId){
        return memberrepository.findByEmailId(emailId).orElseThrow(RuntimeException::new); //익셉션 추가예정
    }
}
