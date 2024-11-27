package dreamteam.hitthebook.domain.timer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.entity.TimerHistory;
import dreamteam.hitthebook.domain.timer.helper.DurationSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TimerDto {

    @Data
    @NoArgsConstructor
    public static class TimerStartRequestDto{

        @Schema(description = "과목명", example = "수학", type = "string")
        private String subjectName;

    }

    @Data
    @NoArgsConstructor
    public static class TimerPlayRequestDto{

        @Schema(description = "타이머 목표 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        private Duration targetTime;
    }

    @Data
    @NoArgsConstructor
    public static class TimerEndRequestDto{

        @Schema(description = "타이머 종료 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        private Duration studyTimeLength;

        @Schema(description = "타이머 목표 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        private Duration targetTime;

        @Schema(description = "타이머 획득 점수")
        private int score;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimerEndResponseDto {
        private int score;
    }

    @Data
    @NoArgsConstructor
    public static class TimerDateDto{

        @Schema(description = "날짜 입력", example = "2024-11-12", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
        private LocalDate studyDate;

    }

    @Data
    @NoArgsConstructor
    public static class TimerListDto {

        private List<TimerContents> timerContentsList = new ArrayList<>();

        public TimerListDto(List<Timer> timerList) {
            for (Timer timer : timerList) {
                this.timerContentsList.add(new TimerContents(timer));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class TimerContents {

        private Long timerId;
        private String subjectName;
        private Duration totalStudyTime;
        private int totalScore;

        public TimerContents(Timer timer) {
            this.timerId = timer.getTimerId();
            this.subjectName = timer.getSubjectName();
            this.totalStudyTime = timer.getTotalStudyTimeLength();
            this.totalScore = timer.getTotalScore();
        }
    }

    @Data
    @NoArgsConstructor
    public static class TotalInfoDto {

        private Duration studyTimeLength;

        private int score;

    }
    @Data
    @NoArgsConstructor
    public static class TimerStatisticsDto {

        private Map<String, Long> statistics;
    }
}