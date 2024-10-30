package dreamteam.hitthebook.domain.timer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dreamteam.hitthebook.domain.timer.entity.Timer;
import dreamteam.hitthebook.domain.timer.enumulation.TimerColorTypeEnum;
import dreamteam.hitthebook.domain.timer.helper.DurationSerializer;
import dreamteam.hitthebook.domain.timer.helper.LocalDateTimeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimerDto {

    @Data
    @NoArgsConstructor
    public static class TimerStartRequestDto{

        @Schema(description = "과목명", example = "수학", type = "string")
        private String subjectName;

        @Schema(description = "목표 시간 (HH:mm:ss 형식)", example = "12:50:00")
        private String timerTargetTime;

        @Schema(description = "타이머 색깔", example = "RED")
        private TimerColorTypeEnum timerColorType;

    }

    @Data
    @NoArgsConstructor
    public static class TimerPlayRequestDto{

        @Schema(description = "현재 시간 = 타이머 시작 시간")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime timerStartTime;

    }

    @Data
    @NoArgsConstructor
    public static class TimerEndRequestDto {

        @Schema(description = "타이머 종료 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        private Duration studyTimeLength;

    }

    @Data
    @NoArgsConstructor
    public static class TimerDateDto{

        @Schema(description = "날짜 입력", example = "2024-08-08", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
        private LocalDate studyDate;

    }

    @Data
    @NoArgsConstructor
    public static class TimerListDto{

        private List<TimerContents> timerContentsList = new ArrayList<>();
        public TimerListDto(List<Timer> timerList) {
            for(Timer timer : timerList) {
                this.timerContentsList.add(new TimerContents(timer));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class TimerContents{

        private Long timerId;
        private String subjectName;
        private Duration studyTimeLength;

        public TimerContents(Timer timer){
            this.timerId = timer.getTimerId();
            this.subjectName = timer.getSubjectName();
            this.studyTimeLength = timer.getStudyTimeLength();
        }
    }

    @Data
    @NoArgsConstructor
    public static class TotalTimeDto {

        @JsonSerialize(using = DurationSerializer.class)
        private Duration studyTimeLength;

    }
    @Data
    @NoArgsConstructor
    public static class TimerStatisticsDto {
        private Map<String, Long> statistics;
    }
}
