package dreamteam.hitthebook.domain.timer.helper;

import dreamteam.hitthebook.common.exception.DuplicateSubjectNameException;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.domain.alert.repository.AlertRepository;
import dreamteam.hitthebook.domain.login.entity.Member;
import dreamteam.hitthebook.domain.login.repository.MemberRepository;
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

    public void createTimerHistory(Timer timer, TimerHistoryRequestDto timerHistoryRequestDto, Member member){
        TimerHistory timerHistory = TimerHistory.createByTimerHistoryRequestDto(timer, timerHistoryRequestDto, member);
        timerHistoryRepository.save(timerHistory);
    }

    public void updateTimerData(Timer timer, TimerHistoryRequestDto timerHistoryRequestDto) {
        timer.setTotalStudyTimeLength(timer.getTotalStudyTimeLength().plus(timerHistoryRequestDto.getStudyTimeLengthAsDuration()));
        timer.setTotalScore(timer.getTotalScore() + timerHistoryRequestDto.getScore());
        timerRepository.save(timer);
        createTimerHistory(timer, timerHistoryRequestDto, timer.getMember());
    }

    public void updateMemberScore(TimerHistoryRequestDto timerHistoryRequestDto, Member member){
        Long newPoint = member.getPoint() + (long) timerHistoryRequestDto.getScore();
        member.setPoint(newPoint);
        updateMemberLevel(newPoint);
        memberRepository.save(member);
    }

    public void updateMemberLevel(Long presentPoint){
        // 레벨갱신 로직
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

    public List<Timer> findTimerListByMember(Member member){
        return timerRepository.findByMember(member);
    }

    public List<TimerHistory> findTodayTimerHistoryByMember(Member member) {
//        List<TimerHistory> timerHistoryList = new ArrayList<>();
//        List<Timer> timerList = findTimerListByMember(member);
//        for (Timer timer : timerList) {
//            timerHistoryList.addAll(timerHistoryRepository.findByTimerAndToday(timer));
//        }
//        return timerHistoryList; -> db검색이 지나치게 많아서 비효율적인 방법이므로 jpa에서 jpql로 해결시도
        return timerHistoryRepository.findTodayTimerHistoryByMember(member);
    }

    public TodayTimerDataDto toTodayTimerDataDto(Member member) {
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
//        Duration sumDuration = Duration.ZERO;
//        List<LocalDate> targetWeek1 = findWeekRangeByDate(targetDate.minusDays(21));
//        List<LocalDate> targetWeek2 = findWeekRangeByDate(targetDate.minusDays(14));
//        List<LocalDate> targetWeek3 = findWeekRangeByDate(targetDate.minusDays(7));
//        List<LocalDate> targetWeek4 = findWeekRangeByDate(targetDate);
//        for (LocalDate targetWeek : targetWeek1) {
//            List<Duration> dayDurationList = timerHistoryRepository.findStudyTimeLengthsByMemberAndDate(member, targetDate.getYear(),
//                    targetDate.getMonthValue(), targetDate.getDayOfMonth());
//            for (Duration dayDuration : dayDurationList) {
//                sumDuration = sumDuration.plus(dayDuration);
//            }
//        }
//        for (LocalDate targetWeek : targetWeek2) {
//            List<Duration> dayDurationList = timerHistoryRepository.findStudyTimeLengthsByMemberAndDate(member, targetDate.getYear(),
//                    targetDate.getMonthValue(), targetDate.getDayOfMonth());
//            for (Duration dayDuration : dayDurationList) {
//                sumDuration = sumDuration.plus(dayDuration);
//            }
//        }
//        for (LocalDate targetWeek : targetWeek3) {
//            List<Duration> dayDurationList = timerHistoryRepository.findStudyTimeLengthsByMemberAndDate(member, targetDate.getYear(),
//                    targetDate.getMonthValue(), targetDate.getDayOfMonth());
//            for (Duration dayDuration : dayDurationList) {
//                sumDuration = sumDuration.plus(dayDuration);
//            }
//        }
//        for (LocalDate targetWeek : targetWeek4) {
//            List<Duration> dayDurationList = timerHistoryRepository.findStudyTimeLengthsByMemberAndDate(member, targetDate.getYear(),
//                    targetDate.getMonthValue(), targetDate.getDayOfMonth());
//            for (Duration dayDuration : dayDurationList) {
//                sumDuration = sumDuration.plus(dayDuration);
//            }
//        }
//        return sumDuration;

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