package dreamteam.hitthebook.domain.dday.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import dreamteam.hitthebook.domain.dday.entity.Dday;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DdayDto {
    @Data
    @NoArgsConstructor
    public static class DdayRequestDto{
        @NotBlank
        private String ddayName;

        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime startDate;

        @NotNull
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

    @Data
    @NoArgsConstructor
    public static class DdayListDto{
        private String message;
        private DdayContents primaryDday;
        private List<DdayContents> upComingDdays = new ArrayList<>();
        private List<DdayContents> oldDdays = new ArrayList<>();
        public DdayListDto(String message, DdayContents primaryDday, List<Dday> upComingDdays, List<Dday> oldDdays) {
            this.message = message;
            this.primaryDday = primaryDday;
            for(Dday dday : upComingDdays) {
                this.upComingDdays.add(new DdayContents(dday));
            }
            for(Dday dday : oldDdays) {
                this.oldDdays.add(new DdayContents(dday));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class DdayContents{
        private Long ddayId;
        private String ddayName;
        private Integer remainingDays;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        public DdayContents(Dday dday) {
            this.ddayId = dday.getDdayId();
            this.ddayName = dday.getDdayName();
            this.remainingDays = dday.getRemainingDays();
            this.startDate = dday.getStartDate();
            this.endDate = dday.getEndDate();
        }
    }
}
