package dreamteam.hitthebook.configuration;


import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.dday.entity.Dday;
import dreamteam.hitthebook.domain.dday.repository.DdayRepository;
import dreamteam.hitthebook.domain.member.entity.Emblem;
import dreamteam.hitthebook.domain.member.entity.Inventory;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.enumulation.EmblemEnumlation;
import dreamteam.hitthebook.domain.member.repository.EmblemRepository;
import dreamteam.hitthebook.domain.member.repository.InventoryRepository;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.ScheduleTypeEnum;
import dreamteam.hitthebook.domain.plannerschedule.repository.PlannerScheduleRepository;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.repository.TimerHistoryRepository;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Duration;
import java.time.LocalDateTime;

// 초기데이터 생성에 있어서 굳이 configuration으로 관리할 이유가 없음.
@Component
@Slf4j
@RequiredArgsConstructor
public class TestDataInitConfig {
    private final MemberRepository memberRepository;
    private final DdayRepository ddayRepository;
    private final PlannerScheduleRepository plannerScheduleRepository;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final TimerRepository timerRepository;
    private final TimerHistoryRepository timerHistoryRepository;
    private final EmblemRepository emblemRepository;
    private final InventoryRepository inventoryRepository;
    private final AlertRepository alertRepository;

    @PostConstruct
    @Transactional
    public void memberDataInit() {
        log.info("Initializing test data");

        // member test data init

        Member member1 = new Member();
        member1.setEmailId("test1@example.com");
        member1.setNickname("뉴진스");
        member1.setPassword(passwordEncoder.encode("qwer1234"));
        memberRepository.save(member1);

        Member member2= new Member();
        member2.setEmailId("test2@example.com");
        member2.setNickname("에스파");
        member2.setPassword(passwordEncoder.encode("qwer1234"));
        memberRepository.save(member2);

        Member member3 = new Member();
        member3.setEmailId("test3@example.com");
        member3.setNickname("아이브");
        member3.setLevel(2);
        member3.setPoint(333);
        member3.setPassword(passwordEncoder.encode("qwer1234"));
        memberRepository.save(member3);

        Member member4 = new Member();
        member4.setEmailId("test4@example.com");
        member4.setNickname("예비군");
        member4.setPassword(passwordEncoder.encode("qwer1234"));
        memberRepository.save(member4);

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
        plannerSchedule1.setScheduleFeedback(FeedbackTypeEnum.FAILED);
        plannerSchedule1.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule1);

        PlannerSchedule plannerSchedule2 = new PlannerSchedule();
        plannerSchedule2.setScheduleAt(LocalDateTime.now());
        plannerSchedule2.setStartAt(LocalDateTime.now().minusHours(2));
        plannerSchedule2.setEndAt(LocalDateTime.now().plusHours(1));
        plannerSchedule2.setScheduleTitle("동네친구들 술약속");
        plannerSchedule2.setScheduleContent("오늘 오랜만에 동네친구들이랑 모이는 날 봉수육 가서 수육한잔과 소주 크~ 캬~");
        plannerSchedule2.setScheduleType(ScheduleTypeEnum.EVENT);
        plannerSchedule2.setScheduleFeedback(FeedbackTypeEnum.FAILED);
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
        plannerSchedule6.setScheduleFeedback(FeedbackTypeEnum.FAILED);
        plannerSchedule6.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule6);

        PlannerSchedule plannerSchedule7 = new PlannerSchedule();
        plannerSchedule7.setScheduleAt(LocalDateTime.now().plusDays(1));
        plannerSchedule7.setStartAt(LocalDateTime.now().plusHours(1));
        plannerSchedule7.setEndAt(LocalDateTime.now().plusHours(2));
        plannerSchedule7.setScheduleTitle("백준 4문제 풀기");
        plannerSchedule7.setScheduleContent("플래너 알림 확인용");
        plannerSchedule7.setScheduleType(ScheduleTypeEnum.SUBJECT);
        plannerSchedule7.setScheduleFeedback(FeedbackTypeEnum.FAILED);
        plannerSchedule7.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule7);

        PlannerSchedule plannerSchedule8 = new PlannerSchedule();
        plannerSchedule8.setScheduleAt(LocalDateTime.now().plusDays(1));
        plannerSchedule8.setStartAt(LocalDateTime.now().plusHours(5));
        plannerSchedule8.setEndAt(LocalDateTime.now().plusHours(6));
        plannerSchedule8.setScheduleTitle("술약속 빰빠빰빠라라빰");
        plannerSchedule8.setScheduleContent("플래너 알림 확인용");
        plannerSchedule8.setScheduleType(ScheduleTypeEnum.EVENT);
        plannerSchedule8.setScheduleFeedback(FeedbackTypeEnum.FAILED);
        plannerSchedule8.setMember(member3);
        plannerScheduleRepository.save(plannerSchedule8);


        //timer data init
        Timer timer1 = new Timer();
        timer1.setSubjectName("스프링부트");
        timer1.setTotalStudyTimeLength(Duration.ofHours(7));
        timer1.setTotalScore(510);
        timer1.setMember(member3);
        timerRepository.save(timer1);

        TimerHistory timerHistory1 = new TimerHistory();
        timerHistory1.setScore(70);
        timerHistory1.setStudyTimeLength(Duration.ofHours(1));
        timerHistory1.setStudyTime(LocalDateTime.now());
        timerHistory1.setTargetTime(Duration.ofMinutes(50));
        timerHistory1.setMember(member3);
        timerHistory1.setTimer(timer1);
        timerHistoryRepository.save(timerHistory1);

        TimerHistory timerHistory2 = new TimerHistory();
        timerHistory2.setScore(70);
        timerHistory2.setStudyTimeLength(Duration.ofHours(1));
        timerHistory2.setStudyTime(LocalDateTime.now().minusDays(1));
        timerHistory2.setTargetTime(Duration.ofMinutes(50));
        timerHistory2.setMember(member3);
        timerHistory2.setTimer(timer1);
        timerHistoryRepository.save(timerHistory2);

        TimerHistory timerHistory3 = new TimerHistory();
        timerHistory3.setScore(70);
        timerHistory3.setStudyTimeLength(Duration.ofMinutes(50));
        timerHistory3.setStudyTime(LocalDateTime.now().minusDays(3));
        timerHistory3.setTargetTime(Duration.ofMinutes(30));
        timerHistory3.setMember(member3);
        timerHistory3.setTimer(timer1);
        timerHistoryRepository.save(timerHistory3);

        TimerHistory timerHistory4 = new TimerHistory();
        timerHistory4.setScore(70);
        timerHistory4.setStudyTimeLength(Duration.ofMinutes(50));
        timerHistory4.setStudyTime(LocalDateTime.now().minusDays(4));
        timerHistory4.setTargetTime(Duration.ofMinutes(30));
        timerHistory4.setMember(member3);
        timerHistory4.setTimer(timer1);
        timerHistoryRepository.save(timerHistory4);

        TimerHistory timerHistory5 = new TimerHistory();
        timerHistory5.setScore(10);
        timerHistory5.setStudyTimeLength(Duration.ofMinutes(10));
        timerHistory5.setStudyTime(LocalDateTime.now().minusDays(5));
        timerHistory5.setTargetTime(Duration.ofMinutes(10));
        timerHistory5.setMember(member3);
        timerHistory5.setTimer(timer1);
        timerHistoryRepository.save(timerHistory5);

        TimerHistory timerHistory6 = new TimerHistory();
        timerHistory6.setScore(0);
        timerHistory6.setStudyTimeLength(Duration.ofMinutes(50));
        timerHistory6.setStudyTime(LocalDateTime.now().minusDays(6));
        timerHistory6.setTargetTime(Duration.ofMinutes(55));
        timerHistory6.setMember(member3);
        timerHistory6.setTimer(timer1);
        timerHistoryRepository.save(timerHistory6);

        TimerHistory timerHistory7 = new TimerHistory();
        timerHistory7.setScore(0);
        timerHistory7.setStudyTimeLength(Duration.ofMinutes(20));
        timerHistory7.setStudyTime(LocalDateTime.now().minusDays(7));
        timerHistory7.setTargetTime(Duration.ofMinutes(50));
        timerHistory7.setMember(member3);
        timerHistory7.setTimer(timer1);
        timerHistoryRepository.save(timerHistory7);

        TimerHistory timerHistory8 = new TimerHistory();
        timerHistory8.setScore(220);
        timerHistory8.setStudyTimeLength(Duration.ofHours(2));
        timerHistory8.setStudyTime(LocalDateTime.now().minusDays(12));
        timerHistory8.setTargetTime(Duration.ofMinutes(20));
        timerHistory8.setMember(member3);
        timerHistory8.setTimer(timer1);
        timerHistoryRepository.save(timerHistory8);


        Timer timer2 = new Timer();
        timer2.setSubjectName("플러터");
        timer2.setTotalStudyTimeLength(Duration.ofHours(0));
        timer2.setTotalScore(0);
        timer2.setMember(member3);
        timerRepository.save(timer2);

        Timer timer3 = new Timer();
        timer3.setSubjectName("리액트");
        timer3.setTotalStudyTimeLength(Duration.ofMinutes(35));
        timer3.setTotalScore(45);
        timer3.setMember(member3);
        timerRepository.save(timer3);

        TimerHistory timerHistory9 = new TimerHistory();
        timerHistory9.setScore(45);
        timerHistory9.setStudyTimeLength(Duration.ofMinutes(35));
        timerHistory9.setStudyTime(LocalDateTime.now().minusDays(1));
        timerHistory9.setTargetTime(Duration.ofMinutes(25));
        timerHistory9.setMember(member3);
        timerHistory9.setTimer(timer3);
        timerHistoryRepository.save(timerHistory9);


        //emblem init data

        Emblem emblem1 = new Emblem();
        emblem1.setEmblemName(EmblemEnumlation.TIMERSUBJECT1H);
        emblem1.setEmblemContent("누적 1시간 달성(과목)");
        emblemRepository.save(emblem1);

        Emblem emblem2 = new Emblem();
        emblem2.setEmblemName(EmblemEnumlation.TIMERSUBJECT2H);
        emblem2.setEmblemContent("누적 2시간 달성(과목)");
        emblemRepository.save(emblem2);

        Emblem emblem3 = new Emblem();
        emblem3.setEmblemName(EmblemEnumlation.TIMERSUBJECT3H);
        emblem3.setEmblemContent("누적 3시간 달성(과목)");
        emblemRepository.save(emblem3);

        Emblem emblem4 = new Emblem();
        emblem4.setEmblemName(EmblemEnumlation.TIMERSUBJECT15M);
        emblem4.setEmblemContent("누적 15분 달성(과목)");
        emblemRepository.save(emblem4);

        Emblem emblem5 = new Emblem();
        emblem5.setEmblemName(EmblemEnumlation.TIMERSUBJECT30M);
        emblem5.setEmblemContent("누적 30분 달성(과목)");
        emblemRepository.save(emblem5);

        Emblem emblem6 = new Emblem();
        emblem6.setEmblemName(EmblemEnumlation.PLANNERSEQUENCE2D);
        emblem6.setEmblemContent("연속 2일 플래너 사용");
        emblemRepository.save(emblem6);

        Emblem emblem7 = new Emblem();
        emblem7.setEmblemName(EmblemEnumlation.PLANNERSEQUENCE7D);
        emblem7.setEmblemContent("연속 7일 플래너 사용");
        emblemRepository.save(emblem7);

        Emblem emblem8 = new Emblem();
        emblem8.setEmblemName(EmblemEnumlation.PLANNERSEQUENCE14D);
        emblem8.setEmblemContent("연속 14일 플래너 사용");
        emblemRepository.save(emblem8);

        Emblem emblem9 = new Emblem();
        emblem9.setEmblemName(EmblemEnumlation.PLANNERSEQUENCE28D);
        emblem9.setEmblemContent("연속 28일 플래너 사용");
        emblemRepository.save(emblem9);

        Emblem emblem10 = new Emblem();
        emblem10.setEmblemName(EmblemEnumlation.PLANNERSTUDYFIRST);
        emblem10.setEmblemContent("첫 공부 플래너 추가");
        emblemRepository.save(emblem10);

        Emblem emblem11 = new Emblem();
        emblem11.setEmblemName(EmblemEnumlation.PLANNERSCHEDULEFIRST);
        emblem11.setEmblemContent("첫 일정 플래너 추가");
        emblemRepository.save(emblem11);

        Emblem emblem12 = new Emblem();
        emblem12.setEmblemName(EmblemEnumlation.DDAYFIRST);
        emblem12.setEmblemContent("첫 디데이 추가");
        emblemRepository.save(emblem12);

        Emblem emblem13 = new Emblem();
        emblem13.setEmblemName(EmblemEnumlation.TIMERGOALFIRST);
        emblem13.setEmblemContent("타이머 첫 목표 달성");
        emblemRepository.save(emblem13);

        Emblem emblem14 = new Emblem();
        emblem14.setEmblemName(EmblemEnumlation.TIMERFIRST);
        emblem14.setEmblemContent("첫 타이머 시작");
        emblemRepository.save(emblem14);

        Emblem emblem15 = new Emblem();
        emblem15.setEmblemName(EmblemEnumlation.TIMERTOTAL6H);
        emblem15.setEmblemContent("타이머 누적 6시간 달성");
        emblemRepository.save(emblem15);

        Emblem emblem16 = new Emblem();
        emblem16.setEmblemName(EmblemEnumlation.TIMERTOTAL9H);
        emblem16.setEmblemContent("타이머 누적 9시간 달성");
        emblemRepository.save(emblem16);

        Emblem emblem17 = new Emblem();
        emblem17.setEmblemName(EmblemEnumlation.TIMERTOTAL12H);
        emblem17.setEmblemContent("타이머 누적 12시간 달성");
        emblemRepository.save(emblem17);

        Emblem emblem18 = new Emblem();
        emblem18.setEmblemName(EmblemEnumlation.TIMERTOTAL15H);
        emblem18.setEmblemContent("타이머 누적 15시간 달성");
        emblemRepository.save(emblem18);

        Emblem emblem19 = new Emblem();
        emblem19.setEmblemName(EmblemEnumlation.TIMERTOTAL20H);
        emblem19.setEmblemContent("타이머 누적 20시간 달성");
        emblemRepository.save(emblem19);

        Emblem emblem20 = new Emblem();
        emblem20.setEmblemName(EmblemEnumlation.TIMERTOTAL30H);
        emblem20.setEmblemContent("타이머 누적 30시간 달성");
        emblemRepository.save(emblem20);

        Emblem emblem21 = new Emblem();
        emblem21.setEmblemName(EmblemEnumlation.TIMERTOTAL50H);
        emblem21.setEmblemContent("타이머 누적 50시간 달성");
        emblemRepository.save(emblem21);

        Emblem emblem22 = new Emblem();
        emblem22.setEmblemName(EmblemEnumlation.TIMERTOTAL50H);
        emblem22.setEmblemContent("타이머 누적 100시간 달성");
        emblemRepository.save(emblem22);

        Emblem emblem23 = new Emblem();
        emblem23.setEmblemName(EmblemEnumlation.TIMERTOTAL50H);
        emblem23.setEmblemContent("타이머 누적 200시간 달성");
        emblemRepository.save(emblem23);

        Emblem emblem24 = new Emblem();
        emblem24.setEmblemName(EmblemEnumlation.TIMERTOTAL50H);
        emblem24.setEmblemContent("타이머 누적 300시간 달성");
        emblemRepository.save(emblem24);

        Emblem emblem25 = new Emblem();
        emblem25.setEmblemName(EmblemEnumlation.TIMERTOTAL50H);
        emblem25.setEmblemContent("타이머 누적 400시간 달성");
        emblemRepository.save(emblem25);

        Inventory inventory1 = new Inventory();
        inventory1.setMember(member3);
        inventory1.setEmblem(emblem1);
        inventoryRepository.save(inventory1);

        Inventory inventory2 = new Inventory();
        inventory2.setMember(member3);
        inventory2.setEmblem(emblem2);
        inventoryRepository.save(inventory2);

        Inventory inventory3 = new Inventory();
        inventory3.setMember(member3);
        inventory3.setEmblem(emblem3);
        inventoryRepository.save(inventory3);

        Inventory inventory4 = new Inventory();
        inventory4.setMember(member3);
        inventory4.setEmblem(emblem4);
        inventoryRepository.save(inventory4);

        Inventory inventory5 = new Inventory();
        inventory5.setMember(member3);
        inventory5.setEmblem(emblem5);
        inventoryRepository.save(inventory5);


        // alert test data
        Alert alert1 = new Alert();
        alert1.setMember(member3);
        alert1.setAlertTitle("[긴급] 타이머 화면 오류");
        alert1.setAlertContent("타이머 화면 오류로 인하여 오류 수정 중입니다. 양해부탁드립니다.");
        alert1.setAlertType(AlertTypeEnum.NOTICE);
        alertRepository.save(alert1);

        Alert alert2 = new Alert();
        alert2.setMember(member3);
        alert2.setAlertTitle("[긴급] 플래너 기능 오류");
        alert2.setAlertContent("플래너 일정 등록 기능 작동 오류로 인하여 오류 수정 중입니다. 양해부탁드립니다.");
        alert2.setAlertType(AlertTypeEnum.NOTICE);
        alertRepository.save(alert2);

        Alert alert3 = new Alert();
        alert3.setMember(member3);
        alert3.setAlertTitle("BLUE Ⅴ에서 BLUE Ⅳ로 승급하였습니다.");
        alert3.setAlertType(AlertTypeEnum.LEVEL);
        alertRepository.save(alert3);

        Alert alert4 = new Alert();
        alert4.setMember(member3);
        alert4.setAlertTitle("누적 15분 달성(과목) 엠블럼을 획득하였습니다.");
        alert4.setAlertType(AlertTypeEnum.EMBLEM);
        alertRepository.save(alert4);

        Alert alert5 = new Alert();
        alert5.setMember(member3);
        alert5.setAlertTitle("누적 30분 달성(과목) 엠블럼을 획득하였습니다.");
        alert5.setAlertType(AlertTypeEnum.EMBLEM);
        alertRepository.save(alert5);

        Alert alert6 = new Alert();
        alert6.setMember(member3);
        alert6.setAlertTitle("누적 1시간 달성(과목) 엠블럼을 획득하였습니다.");
        alert6.setAlertType(AlertTypeEnum.EMBLEM);
        alertRepository.save(alert6);

        Alert alert7 = new Alert();
        alert7.setMember(member3);
        alert7.setAlertTitle("누적 2시간 달성(과목) 엠블럼을 획득하였습니다.");
        alert7.setAlertType(AlertTypeEnum.EMBLEM);
        alertRepository.save(alert7);

        Alert alert8 = new Alert();
        alert8.setMember(member3);
        alert8.setAlertTitle("누적 3시간 달성(과목) 엠블럼을 획득하였습니다.");
        alert8.setAlertType(AlertTypeEnum.EMBLEM);
        alertRepository.save(alert8);

        Alert alert9 = new Alert();
        alert9.setMember(member3);
        alert9.setAlertTitle("수학과목 목표시간을 달성해 0점을 획득하셨습니다.");
        alert9.setAlertType(AlertTypeEnum.TIMER);
        alertRepository.save(alert9);

        Alert alert10 = new Alert();
        alert10.setMember(member3);
        alert10.setAlertTitle("친구들과 술약속 모임이 내일 예정되어 있습니다.");
        alert10.setAlertType(AlertTypeEnum.PLANNER);
        alertRepository.save(alert10);

        log.info("Test data initialized");
    }

}
