package dreamteam.hitthebook.domain.timer.helper;

import dreamteam.hitthebook.common.exception.DuplicateSubjectNameException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.common.commonutil.Level;
import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.enumulation.AlertTypeEnum;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.repository.TimerHistoryRepository;
import dreamteam.hitthebook.domain.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static dreamteam.hitthebook.domain.timer.dto.TimerDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TimerHelper {
    private final MemberRepository memberRepository;
    private final TimerRepository timerRepository;
    private final TimerHistoryRepository timerHistoryRepository;
    private final AlertRepository alertRepository;
    private final List<Level> levels;

    // 이메일 아이디로 멤버 검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    // 타이머 아이디로 타이머 검색
    public Timer findTimerByTimerId(Long timerId){
        return timerRepository.findById(timerId).orElseThrow(ResourceNotFoundException::new);
    }

    // 같은 과목이름이 있는지 검사
    public void checkSameSubjectName(String subjectName){
        if(timerRepository.findBySubjectName(subjectName).isPresent()){
            throw new DuplicateSubjectNameException();
        }
    }

    // 타이머 히스토리 생성
    public TimerHistory createTimerHistory(Timer timer, TimerHistoryRequestDto timerHistoryRequestDto, Member member){
        TimerHistory timerHistory = TimerHistory.createByTimerHistoryRequestDto(timer, timerHistoryRequestDto, member);
        timerHistoryRepository.save(timerHistory);
        return timerHistory;
    }

    // 타이머 데이터 갱신
    public void updateTimerData(Timer timer, TimerHistoryRequestDto timerHistoryRequestDto) {
        timer.setTotalStudyTimeLength(timer.getTotalStudyTimeLength().plus(timerHistoryRequestDto.getStudyTimeLengthAsDuration()));
        timer.setTotalScore(timer.getTotalScore() + timerHistoryRequestDto.getScore());
        timerRepository.save(timer);
    }

    // 멤버필드에 타이머 관련 데이터 업데이트
    public void updateMemberTimerData(TimerHistoryRequestDto timerHistoryRequestDto, Member member){
        int newPoint = member.getPoint() + timerHistoryRequestDto.getScore();
        Duration newDuration = member.getAllStudyTime().plus(timerHistoryRequestDto.getStudyTimeLengthAsDuration());
        Level beforeLevel = findLevelByPoints(member.getPoint());
        createTimerAlert(member, timerHistoryRequestDto);
        member.setPoint(newPoint);
        member.setAllStudyTime(newDuration);
        updateMemberLevel(beforeLevel, member);
        memberRepository.save(member);
    }

    // 타이머 알림 생성
    public void createTimerAlert(Member member, TimerHistoryRequestDto timerHistoryRequestDto) {
        if (timerHistoryRequestDto.getStudyTimeLengthAsDuration().compareTo(timerHistoryRequestDto.getTargetTimeAsDuration()) >= 0) {
            Alert alert = new Alert(member);
            alert.setAlertTitle("목표시간을 달성해 " + timerHistoryRequestDto.getScore() + "점을 획득하셨습니다.");
            alert.setAlertType(AlertTypeEnum.TIMER);
            alertRepository.save(alert);
        }
    }

    // 포인트에 맞는 레벨 검색
    public Level findLevelByPoints(int points) {
        return levels.stream()
                .filter(level -> points >= level.getMinPoint() && points <= level.getMaxPoint())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point data is invalid"));
    }

    // 레벨알림 생성
    public void createLevelAlert(Member member, Level beforeLevel, Level afterLevel){
        if(beforeLevel.getLevel() != afterLevel.getLevel()){
            Alert alert = new Alert(member, afterLevel.getLevel(), beforeLevel.getLevelName(), afterLevel.getLevelName());
            alertRepository.save(alert);
        }
    }

    // 멤버레벨 업데이트
    public void updateMemberLevel(Level beforeLevel, Member member){
        int points = member.getPoint();
        Level afterLevel = findLevelByPoints(points);
        member.setLevel(afterLevel.getLevel());
        createLevelAlert(member, beforeLevel, afterLevel);
        memberRepository.save(member);
    }

    // 타이머 수정 권한이 있는지 확인
    public void checkTimerEditPermission(Timer timer, Member member){
        if(!(timer.getMember().equals(member))){throw new RuntimeException();}
    }

    // 타이머로 타이머 히스토리들 삭제(카스케이드 없이 직접)
    public void deleteTimerHistoryByTimerId(Timer timer) {
        List<TimerHistory> timerHistoryList = timerHistoryRepository.findByTimer(timer).orElseThrow(ResourceNotFoundException::new);
        timerHistoryRepository.deleteAll(timerHistoryList);
    }

    // 타이머 엔티티 삭제
    public void deleteTimerEntity(Timer timer) {
        timerRepository.delete(timer);
    }

    // 타이머리스트dto로 변환
    public TimerListDto toTimerListDto(Member member){
        return new TimerListDto(timerRepository.findByMember(member));
    }

    // 타이머 과목 이름 수정
    public void updateTimerName(Timer timer, String subjectName) {
        timer.setSubjectName(subjectName);
        timerRepository.save(timer);
    }

    // 해당 멤버의 오늘 타이머 히스토리 찾기
    public List<TimerHistory> findTodayTimerHistoryByMember(Member member) {
        return timerHistoryRepository.findTodayTimerHistoryByMember(member);
    }

    // 오늘의 타이머 데이터로 변환
    public TodayTimerDataDto toTodayTimerDataDto(Member member) {
        return new TodayTimerDataDto(findTodayTimerHistoryByMember(member), member.getLevel());
    }

    // 날짜로 주간 범위 찾기
    public List<LocalDate> findWeekRangeByDate(LocalDate targetDate){
        LocalDate monday = targetDate.minusDays(targetDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
        List<LocalDate> targetWeek = new ArrayList<>();
        for (int i = 0; i < 7; i++) {targetWeek.add(monday.plusDays(i));}
        return targetWeek;
    }

    // 날짜로 일간 총 공부 시간 구하기
    public List<Duration> getTotalDailyStudyTimeByDate(Member member, LocalDate targetDate){
        List<LocalDate> targetWeek = findWeekRangeByDate(targetDate);
        // 각 날짜별 공부 시간 계산
        return targetWeek.stream()
                .map(date -> timerHistoryRepository.findStudyTimeLengthsByMemberAndDate(
                                member, date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                        .stream()
                        .reduce(Duration.ZERO, Duration::plus)) // 각 날짜별 합계
                .collect(Collectors.toList());
    }

    // 날짜로 일간 총 공부 시간 dto로 변환
    public TargetDateDailyStatistics toTotalTargetDateDailyStatistics(Member member, LocalDate targetDate){
        List<Duration> dailySumData = getTotalDailyStudyTimeByDate(member, targetDate);
        return new TargetDateDailyStatistics(dailySumData.get(0),dailySumData.get(1),dailySumData.get(2),
                dailySumData.get(3),dailySumData.get(4),dailySumData.get(5),dailySumData.get(6));
    }

    // 날짜로 일간 과목별 공부 시간 가져오기
    public List<Duration> getTotalDailyStudyTimeByDateAndSubjectName(Member member, LocalDate targetDate, String subjectName) {
        List<LocalDate> targetWeek = findWeekRangeByDate(targetDate);

        return targetWeek.stream()
                .map(date -> timerHistoryRepository.findStudyTimeLengthsByMemberAndDateAndSubjectName(
                                member, date.getYear(), date.getMonthValue(), date.getDayOfMonth(), subjectName)
                        .stream()
                        .reduce(Duration.ZERO, Duration::plus)) // 각 날짜별 합계
                .collect(Collectors.toList());
    }

    // 날짜로 일간 과목별 공부시간 dto로 변환
    public TargetDateDailyStatistics toSubjectTargetDateDailyStatistics(Member member, LocalDate targetDate, String subjectName){
        List<Duration> dailySumData = getTotalDailyStudyTimeByDateAndSubjectName(member, targetDate, subjectName);
        return new TargetDateDailyStatistics(dailySumData.get(0),dailySumData.get(1),dailySumData.get(2),
                dailySumData.get(3),dailySumData.get(4),dailySumData.get(5),dailySumData.get(6));
    }

    // 날짜로 주별 날짜들에 대한 데이터 구하기
    public List<List<LocalDate>> findWeeklyInformationByDate(Member member, LocalDate targetDate){
        return List.of(
                findWeekRangeByDate(targetDate.minusDays(21)), // 4주 전
                findWeekRangeByDate(targetDate.minusDays(14)), // 3주 전
                findWeekRangeByDate(targetDate.minusDays(7)),  // 2주 전
                findWeekRangeByDate(targetDate)               // 이번 주
        );
    }

    // 날짜로 주간 총 공부시간 구하기
    public List<Duration> getTotalWeeklyStudyTimeByDate(Member member, LocalDate targetDate){
        // 4주간의 날짜 리스트 생성
        List<List<LocalDate>> allWeeks = findWeeklyInformationByDate(member, targetDate);

        // 각 주의 공부 시간을 계산
        return allWeeks.stream()
                .map(week -> week.stream()
                        .map(date -> timerHistoryRepository.findStudyTimeLengthsByMemberAndDate(
                                member, date.getYear(), date.getMonthValue(), date.getDayOfMonth()))
                        .flatMap(List::stream) // 각 날짜의 공부 시간을 하나의 스트림으로 변환
                        .reduce(Duration.ZERO, Duration::plus)) // 주간 합계 계산
                .collect(Collectors.toList()); // 4주치 결과 리스트로 반환
    }

    // 날짜로 주간 총 공부시간 dto로 변환
    public TargetDateWeeklyStatistics toTotalTargetDateWeeklyStatistics(Member member, LocalDate targetDate){
        List<Duration> weeklySumData = getTotalWeeklyStudyTimeByDate(member, targetDate);
        return new TargetDateWeeklyStatistics(weeklySumData.get(0), weeklySumData.get(1), weeklySumData.get(2), weeklySumData.get(3));
    }

    // 날짜로 주간 과목별 공부시간 구하기
    public List<Duration> getTotalWeeklyStudyTimeByDateAndSubjectName(Member member, LocalDate targetDate, String subjectName) {
        List<List<LocalDate>> allWeeks = findWeeklyInformationByDate(member, targetDate);
        return allWeeks.stream()
                .map(week -> week.stream()
                        .map(date -> timerHistoryRepository.findStudyTimeLengthsByMemberAndDateAndSubjectName(member, date.getYear(),
                                date.getMonthValue(), date.getDayOfMonth(), subjectName))
                        .flatMap(List::stream) // 각 날짜의 공부 시간을 하나의 스트림으로 변환
                        .reduce(Duration.ZERO, Duration::plus)) // 주간 합계 계산
                .collect(Collectors.toList()); // 4주치 결과 리스트로 반환
    }

    // 날짜로 주간 과목별 공부시간 dto로 변환
    public TargetDateWeeklyStatistics toSubjectTargetDateWeeklyStatistics(Member member, LocalDate targetDate, String subjectName){
        List<Duration> weeklySumData = getTotalWeeklyStudyTimeByDateAndSubjectName(member, targetDate, subjectName);
        return new TargetDateWeeklyStatistics(weeklySumData.get(0), weeklySumData.get(1), weeklySumData.get(2), weeklySumData.get(3));
    }
}