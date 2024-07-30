package dreamteam.hitthebook.domain.dday.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class DdayDto {
    @Data
    @NoArgsConstructor
    public static class DdayRequestDto{
        private String ddayName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime endDate;
    }

    @Data
    @NoArgsConstructor
    public static class PrimaryDdayDto {
        private String message;
        private String ddayName;
        private Integer remainingDays;
        public PrimaryDdayDto(String message, String ddayName, Integer remainingDays) {
            this.message = message;
            this.ddayName = ddayName;
            this.remainingDays = remainingDays;
        }
    }



}
