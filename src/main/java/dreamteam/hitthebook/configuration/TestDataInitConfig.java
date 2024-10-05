package dreamteam.hitthebook.configuration;


import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class TestDataInitConfig {
    private final MemberRepository memberRepository;
    private final DdayRepository ddayRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;

    @PostConstruct
    @Transactional
    public void memberDataInit() {
        log.info("Initializing test data");

        // member test data init

        Member member1 = new Member();
        member1.setEmailId("test1@example.com");
        member1.setNickname("뉴진스");
        member1.setPassword("password1");
        memberRepository.save(member1);

        Member member2= new Member();
        member2.setEmailId("test2@example.com");
        member2.setNickname("에스파");
        member2.setPassword("password2");
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setEmailId("test3@example.com");
        member3.setNickname("아이브");
        member3.setPassword("password3");
        memberRepository.save(member3);

        // dday test data init

        Dday dday1 = new Dday();
        dday1.setDdayName("OPIC 시험");
        dday1.setStartDate(LocalDateTime.now().plusDays(10));
        dday1.setEndDate(LocalDateTime.now().plusDays(25));
        dday1.setMember(member3);
        ddayRepository.save(dday1);

        Dday dday2 = new Dday();
        dday2.setDdayName("정보처리기사 자격증 시험");
        dday2.setStartDate(LocalDateTime.now().minusDays(100));
        dday2.setEndDate(LocalDateTime.now().plusDays(18));
        dday2.setMember(member3);
        ddayRepository.save(dday2);

        Dday dday3 = new Dday();
        dday3.setDdayName("아이브 음악방송 본방사수");
        dday3.setStartDate(LocalDateTime.now());
        dday3.setEndDate(LocalDateTime.now().plusDays(3));
        dday3.setMember(member3);
        ddayRepository.save(dday3);

        Dday dday4 = new Dday();
        dday4.setDdayName("TOEIC 시험");
        dday4.setStartDate(LocalDateTime.now());
        dday4.setEndDate(LocalDateTime.now().plusDays(150));
        dday4.setMember(member3);
        ddayRepository.save(dday4);

        Dday dday5 = new Dday();
        dday5.setDdayName("졸업");
        dday5.setStartDate(LocalDateTime.now().minusYears(2));
        dday5.setEndDate(LocalDateTime.now().minusDays(45));
        dday5.setMember(member3);
        dday5.setPrimary(true);
        ddayRepository.save(dday5);

        // planner test data init
        PlannerSchedule plannerSchedule1 = new PlannerSchedule();
        plannerSchedule1.setScheduleAt(LocalDateTime.now());
        plannerSchedule1.setStartAt(LocalDateTime.now().minusHours(7));
        plannerSchedule1.setEndAt(LocalDateTime.now().minusHours(5));
        plannerSchedule1.setScheduleTitle("깃허브 플로우 공부하기");
        plannerSchedule1.setScheduleContent("깃허브 작업의 퀄리티를 높이기 위해서 깃허브 플로우에 관해서 공부한다." +
                "깃허브 이슈와 마일스톤에 관해서도 공부해보고 다양한 깃플로우 전략들을 비교해가며" +
                "실제로 현업에서는 어떤식으로 깃허브를 사용하게 될지 미리 연습하는 시간을 가진다.");
        plannerSchedule1.setScheduleType(ScheduleTypeEnum.SUBJECT);
        plannerSchedule1.setScheduleFeedback(FeedbackTypeEnum.NONE);
        plannerSchedule1.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule1);

        PlannerSchedule plannerSchedule2 = new PlannerSchedule();
        plannerSchedule2.setScheduleAt(LocalDateTime.now());
        plannerSchedule2.setStartAt(LocalDateTime.now().minusHours(2));
        plannerSchedule2.setEndAt(LocalDateTime.now().plusHours(1));
        plannerSchedule2.setScheduleTitle("동네친구들 술약속");
        plannerSchedule2.setScheduleContent("오늘 오랜만에 동네친구들이랑 모이는 날 봉수육 가서 수육한잔과 소주 크~ 캬~");
        plannerSchedule2.setScheduleType(ScheduleTypeEnum.EVENT);
        plannerSchedule2.setScheduleFeedback(FeedbackTypeEnum.NONE);
        plannerSchedule2.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule2);

        PlannerSchedule plannerSchedule3 = new PlannerSchedule();
        plannerSchedule3.setScheduleAt(LocalDateTime.now().minusDays(1));
        plannerSchedule3.setStartAt(LocalDateTime.now().minusDays(1).plusHours(1));
        plannerSchedule3.setEndAt(LocalDateTime.now().minusDays(1).plusHours(2));
        plannerSchedule3.setScheduleTitle("플래너 시간 추가 검열 알고리즘 고민하기");
        plannerSchedule3.setScheduleContent("플래너에 시간표를 추가할 때 겹치는 일정이 없도록 검열을 하려고 한다." +
                "하지만 다양한 알고리즘을 생각해 보았을 때 너무 로직적으로 비효율적으로 보인다." +
                "특히 for문이 매번 객체탐색을 하는 상황을 방지하고 싶다." +
                "어떤 알고리즘을 사용해야 가장 로직적으로 깔끔할지 고민해보려고 한다.");
        plannerSchedule3.setScheduleType(ScheduleTypeEnum.SUBJECT);
        plannerSchedule3.setScheduleFeedback(FeedbackTypeEnum.DONE);
        plannerSchedule3.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule3);

        PlannerSchedule plannerSchedule4 = new PlannerSchedule();
        plannerSchedule4.setScheduleAt(LocalDateTime.now().minusDays(1));
        plannerSchedule4.setStartAt(LocalDateTime.now().minusDays(1).minusHours(7));
        plannerSchedule4.setEndAt(LocalDateTime.now().minusDays(1).minusHours(6));
        plannerSchedule4.setScheduleTitle("자바 공부하기");
        plannerSchedule4.setScheduleContent("자바에 대한 이해도가 부족하다고 생각했다." +
                "어떤 병렬처리에 관한 사항의 경우에 람다식 처리를 해야하는데 문법에 관해서 알고 있는 부분이 없어서" +
                "GPT에 의존적인 상황이 나왔는데 GPT에 대한 신뢰가 전혀 가지 않는다." +
                "stream을 이용해 람다식 처리하는 과정을 공부해보자");
        plannerSchedule4.setScheduleType(ScheduleTypeEnum.SUBJECT);
        plannerSchedule4.setScheduleFeedback(FeedbackTypeEnum.PARTIAL);
        plannerSchedule4.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule4);

        PlannerSchedule plannerSchedule5 = new PlannerSchedule();
        plannerSchedule5.setScheduleAt(LocalDateTime.now().minusDays(1));
        plannerSchedule5.setStartAt(LocalDateTime.now().minusDays(1).minusHours(5));
        plannerSchedule5.setEndAt(LocalDateTime.now().minusDays(1).minusHours(4));
        plannerSchedule5.setScheduleTitle("백준 2문제 풀기");
        plannerSchedule5.setScheduleContent("백준문제를 안푼지 너무 오래되서 풀어보려고 한다.");
        plannerSchedule5.setScheduleType(ScheduleTypeEnum.SUBJECT);
        plannerSchedule5.setScheduleFeedback(FeedbackTypeEnum.POSTPONED);
        plannerSchedule5.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule5);

        PlannerSchedule plannerSchedule6 = new PlannerSchedule();
        plannerSchedule6.setScheduleAt(LocalDateTime.now());
        plannerSchedule6.setStartAt(LocalDateTime.now().plusHours(3));
        plannerSchedule6.setEndAt(LocalDateTime.now().plusHours(4));
        plannerSchedule6.setScheduleTitle("백준 2문제 풀기");
        plannerSchedule6.setScheduleContent("백준문제를 안푼지 너무 오래되서 풀어보려고 한다.");
        plannerSchedule6.setScheduleType(ScheduleTypeEnum.SUBJECT);
        plannerSchedule6.setScheduleFeedback(FeedbackTypeEnum.NONE);
        plannerSchedule6.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule6);

        log.info("Test data initialized");
    }

}
