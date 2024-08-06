package dreamteam.hitthebook.domain.timer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimerDto {

    @Data
    @NoArgsConstructor
    public static class TimerRequestDto{

        @Schema(description = "과목명", example = "수학", type = "string")
        private String subjectName; //과목명

        @Schema(description = "타이머 종료 시간", example = "00:00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "UTC")
        private Duration studyTimeLength;

    }
}
