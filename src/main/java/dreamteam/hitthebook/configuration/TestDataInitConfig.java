package dreamteam.hitthebook.configuration;


import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TestDataInitConfig {
    private final MemberRepository memberRepository;

    @PostConstruct
    @Transactional
    public void init() {
        log.info("Initializing test data");

        Member member1 = new Member();
        member1.setEmailId("test1@example.com");
        member1.setNickname("뉴진스");
        member1.setPassword("password1");

        Member member2= new Member();
        member2.setEmailId("test2@example.com");
        member2.setNickname("에스파");
        member2.setPassword("password2");

        Member member3 = new Member();
        member3.setEmailId("test3@example.com");
        member3.setNickname("아이브");
        member3.setPassword("password3");

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        log.info("Test data initialized");
    }
}
