package dreamteam.hitthebook.domain.timer.helper;

import dreamteam.hitthebook.common.exception.DuplicateSubjectNameException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.common.commonutil.Level;
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

    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    public Timer findTimerByTimerId(Long timerId){
        return timerRepository.findById(timerId).orElseThrow(ResourceNotFoundException::new);
    }

    public void checkSameSubjectName(String subjectName){
        if(timerRepository.findBySubjectName(subjectName).isPresent()){
            throw new DuplicateSubjectNameException();
        }
    }

    public TimerHistory createTimerHistory(Timer timer, TimerHistoryRequestDto timerHistoryRequestDto, Member member){
        TimerHistory timerHistory = TimerHistory.createByTimerHistoryRequestDto(timer, timerHistoryRequestDto, member);
        timerHistoryRepository.save(timerHistory);
        return timerHistory;
    }

    public Timer updateTimerData(Timer timer, TimerHistoryRequestDto timerHistoryRequestDto) {
        timer.setTotalStudyTimeLength(timer.getTotalStudyTimeLength().plus(timerHistoryRequestDto.getStudyTimeLengthAsDuration()));
        timer.setTotalScore(timer.getTotalScore() + timerHistoryRequestDto.getScore());
        timerRepository.save(timer);
        return timer;
    }

    public void updateMemberTimerData(TimerHistoryRequestDto timerHistoryRequestDto, Member member){
        int newPoint = member.getPoint() + timerHistoryRequestDto.getScore();
        Duration newDuration = member.getAllStudyTime().plus(timerHistoryRequestDto.getStudyTimeLengthAsDuration());
        member.setPoint(newPoint);
        member.setAllStudyTime(newDuration);
        updateMemberLevel(member);
        memberRepository.save(member);
    }

    public Level findLevelByPoints(int points) {
        return levels.stream()
                .filter(level -> points >= level.getMinPoint() && points <= level.getMaxPoint())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Point data is invalid"));
    }

    public void updateMemberLevel(Member member){
        int points = member.getPoint();
        Level level = findLevelByPoints(points);
        member.setLevel(level.getLevel());
        memberRepository.save(member);
    }

    public void checkTimerEditPermission(Timer timer, Member member){
        if(!(timer.getMember().equals(member))){throw new RuntimeException();}
    }

    public void deleteTimerHistoryByTimerId(Timer timer) {
        List<TimerHistory> timerHistoryList = timerHistoryRepository.findByTimer(timer).orElseThrow(ResourceNotFoundException::new);
        timerHistoryRepository.deleteAll(timerHistoryList);
    }

    public void deleteTimerEntity(Timer timer) {
        timerRepository.delete(timer);
    }

    public TimerListDto toTimerListDto(Member member){
        return new TimerListDto(timerRepository.findByMember(member));
    }

    public void updateTimerName(Timer timer, String subjectName) {
        timer.setSubjectName(subjectName);
        timerRepository.save(timer);
    }

    public List<TimerHistory> findTodayTimerHistoryByMember(Member member) {
        return timerHistoryRepository.findTodayTimerHistoryByMember(member);
    }

    public TodayTimerDataDto toTodayTimerDataDto(Member member) {
        updateMemberLevel(member);
        return new TodayTimerDataDto(findTodayTimerHistoryByMember(member), member.getLevel());
    }

    public List<LocalDate> findWeekRangeByDate(LocalDate targetDate){
        LocalDate monday = targetDate.minusDays(targetDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
        List<LocalDate> targetWeek = new ArrayList<>();
        for (int i = 0; i < 7; i++) {targetWeek.add(monday.plusDays(i));}
        return targetWeek;
    }

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

    public TargetDateDailyStatistics toTotalTargetDateDailyStatistics(Member member, LocalDate targetDate){
        List<Duration> dailySumData = getTotalDailyStudyTimeByDate(member, targetDate);
        return new TargetDateDailyStatistics(dailySumData.get(0),dailySumData.get(1),dailySumData.get(2),
                dailySumData.get(3),dailySumData.get(4),dailySumData.get(5),dailySumData.get(6));
    }

    public List<Duration> getTotalDailyStudyTimeByDateAndSubjectName(Member member, LocalDate targetDate, String subjectName) {
        List<LocalDate> targetWeek = findWeekRangeByDate(targetDate);

        return targetWeek.stream()
                .map(date -> timerHistoryRepository.findStudyTimeLengthsByMemberAndDateAndSubjectName(
                                member, date.getYear(), date.getMonthValue(), date.getDayOfMonth(), subjectName)
                        .stream()
                        .reduce(Duration.ZERO, Duration::plus)) // 각 날짜별 합계
                .collect(Collectors.toList());
    }

    public TargetDateDailyStatistics toSubjectTargetDateDailyStatistics(Member member, LocalDate targetDate, String subjectName){
        List<Duration> dailySumData = getTotalDailyStudyTimeByDateAndSubjectName(member, targetDate, subjectName);
        return new TargetDateDailyStatistics(dailySumData.get(0),dailySumData.get(1),dailySumData.get(2),
                dailySumData.get(3),dailySumData.get(4),dailySumData.get(5),dailySumData.get(6));
    }

    public List<List<LocalDate>> findWeeklyInformationByDate(Member member, LocalDate targetDate){
        return List.of(
                findWeekRangeByDate(targetDate.minusDays(21)), // 4주 전
                findWeekRangeByDate(targetDate.minusDays(14)), // 3주 전
                findWeekRangeByDate(targetDate.minusDays(7)),  // 2주 전
                findWeekRangeByDate(targetDate)               // 이번 주
        );
    }

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

    public TargetDateWeeklyStatistics toTotalTargetDateWeeklyStatistics(Member member, LocalDate targetDate){
        List<Duration> weeklySumData = getTotalWeeklyStudyTimeByDate(member, targetDate);
        return new TargetDateWeeklyStatistics(weeklySumData.get(0), weeklySumData.get(1), weeklySumData.get(2), weeklySumData.get(3));
    }

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

    public TargetDateWeeklyStatistics toSubjectTargetDateWeeklyStatistics(Member member, LocalDate targetDate, String subjectName){
        List<Duration> weeklySumData = getTotalWeeklyStudyTimeByDateAndSubjectName(member, targetDate, subjectName);
        return new TargetDateWeeklyStatistics(weeklySumData.get(0), weeklySumData.get(1), weeklySumData.get(2), weeklySumData.get(3));
    }
}