//package dreamteam.hitthebook.configuration;
//
//import appmakingteam.studymate.domain.member.MemberEntity;
//import appmakingteam.studymate.domain.member.MemberRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.annotation.Transactional;
//
//@Configuration
//@Slf4j
//@RequiredArgsConstructor
//public class TestDataInitConfig {
//    private final MemberRepository memberRepository;
//
//    @PostConstruct
//    @Transactional
//    public void init() {
//        log.info("Initializing test data");
//
//        MemberEntity member1 = new MemberEntity();
//        member1.setEmailId("test1@example.com");
//        member1.setName("흐하하하핫");
//        member1.setPassword("password1");
//
//        MemberEntity member2 = new MemberEntity();
//        member2.setEmailId("test2@example.com");
//        member2.setName("뉴진스화이팅");
//        member2.setPassword("password2");
//
//        MemberEntity member3 = new MemberEntity();
//        member3.setEmailId("test3@example.com");
//        member3.setName("카리나카리나");
//        member3.setPassword("password3");
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//        log.info("Test data initialized");
//    }
//}
