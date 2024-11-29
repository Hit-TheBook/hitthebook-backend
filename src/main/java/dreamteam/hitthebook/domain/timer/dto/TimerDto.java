package dreamteam.hitthebook.domain.timer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimerDto {

    @Data
    @NoArgsConstructor
    public static class TimerHistoryRequestDto {

        @Schema(description = "타이머 종료 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        @NotNull
        private String studyTimeLength;

        @Schema(description = "타이머 목표 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        @NotNull
        private String targetTime;

        @Schema(description = "타이머 획득 점수")
        private int score;

        public Duration getStudyTimeLengthAsDuration() {
            return Duration.between(LocalTime.MIN, LocalTime.parse(this.studyTimeLength));
        }

        public Duration getTargetTimeAsDuration() {
            return Duration.between(LocalTime.MIN, LocalTime.parse(this.targetTime));
        }
    }

    @Data
    @NoArgsConstructor
    public static class TimerListDto {
        private List<TimerContent> timerContentList = new ArrayList<>();

        public TimerListDto(List<Timer> timerList) {
            for (Timer timer : timerList) {
                this.timerContentList.add(new TimerContent(timer));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class TimerContent {
        private Long timerId;
        private String subjectName;
        private Duration totalStudyTime;
        private int totalScore;

        public TimerContent(Timer timer) {
            this.timerId = timer.getTimerId();
            this.subjectName = timer.getSubjectName();
            this.totalStudyTime = timer.getTotalStudyTimeLength();
            this.totalScore = timer.getTotalScore();
        }
    }

    @Data
    @NoArgsConstructor
    public static class TodayTimerDataDto {
        private Duration studyTimeLength = Duration.ZERO;
        private int score = 0;
        private int presentLevel;
        public TodayTimerDataDto(List<TimerHistory> timerHistoryList, int presentLevel) {
            this.presentLevel = presentLevel;
            for (TimerHistory timerHistory : timerHistoryList) {
                this.studyTimeLength = this.studyTimeLength.plus(timerHistory.getStudyTimeLength());
                this.score += timerHistory.getScore();
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class TargetDateDailyStatistics{
        private Duration monday;
        private Duration tuesday;
        private Duration wednesday;
        private Duration thursday;
        private Duration friday;
        private Duration saturday;
        private Duration sunday;
        public TargetDateDailyStatistics(Duration monday, Duration tuesday, Duration wednesday, Duration thursday, Duration friday, Duration saturday, Duration sunday) {
            this.monday = monday;
            this.tuesday = tuesday;
            this.wednesday = wednesday;
            this.thursday = thursday;
            this.friday = friday;
            this.saturday = saturday;
            this.sunday = sunday;
        }
    }

    @Data
    @NoArgsConstructor
    public static class TargetDateWeeklyStatistics{
        private Duration firstWeek;
        private Duration secondWeek;
        private Duration thirdWeek;
        private Duration fourthWeek;
        public TargetDateWeeklyStatistics(Duration firstWeek, Duration secondWeek, Duration thirdWeek, Duration fourthWeek) {
            this.firstWeek = firstWeek;
            this.secondWeek = secondWeek;
            this.thirdWeek = thirdWeek;
            this.fourthWeek = fourthWeek;
        }
    }
}