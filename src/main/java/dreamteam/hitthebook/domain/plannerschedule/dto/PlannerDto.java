package dreamteam.hitthebook.domain.plannerschedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dreamteam.hitthebook.domain.plannerschedule.entity.PlannerSchedule;
import dreamteam.hitthebook.domain.plannerschedule.enumulation.FeedbackTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PlannerDto {
    @Data
    @NoArgsConstructor
    public static class ReviewRequestDto{
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime reviewDate;
    }

    @Data
    @NoArgsConstructor
    public static class ReviewUpdateRequestDto{
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime reviewDate;
    }

    @Data
    @NoArgsConstructor
    public static class ReviewDto{
        private String content = null;
        public ReviewDto(String content) {
            this.content = content;
        }
    }


    @Data
    @NoArgsConstructor
    public static class ScheduleRequestDto{
        private String scheduleTitle;
        private String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime scheduleDate;
    }

    @Data
    @NoArgsConstructor
    public static class DateDto{
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private LocalDateTime scheduleDate;
    }

    @Data
    @NoArgsConstructor
    public static class ScheduleListDto {
        private String message;
        private List<ScheduleContent> schedules = new ArrayList<>();
        public ScheduleListDto(String message, List<PlannerSchedule> schedules) {
            this.message = message;
            for (PlannerSchedule schedule : schedules) {
                this.schedules.add(new ScheduleContent(schedule));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class ScheduleContent {
        private Long plannerScheduleId;
        private String scheduleTitle;
        private String content;
        private FeedbackTypeEnum feedbackType;
        private boolean wasPostponed;
        private LocalDateTime beforeScheduleDate;
        public ScheduleContent(PlannerSchedule plannerSchedule){
            this.plannerScheduleId = plannerSchedule.getPlannerScheduleId();
            this.scheduleTitle = plannerSchedule.getScheduleTitle();
            this.content = plannerSchedule.getScheduleContent();
            this.feedbackType = plannerSchedule.getScheduleFeedback();
            PlannerSchedule beforeSchedule = plannerSchedule.getBeforeSchedule();
            this.wasPostponed = beforeSchedule != null;
            this.beforeScheduleDate = (beforeSchedule != null) ? beforeSchedule.getScheduleAt() : null;
        }
    }

}
