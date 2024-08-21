package dreamteam.hitthebook.domain.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.assignment.enumulation.AssignmentDayOfWeekEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class AssignmentDto {

    @Data
    @NoArgsConstructor
    public static class AssignmentRequestDto{
        private List<AssignmentDayOfWeekEnum> dayOfWeekEnumList;

        private String assignmentContent;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
        private LocalDate assignmentStartAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
        private LocalDate assignmentEndAt;
    }

    @Data
    @NoArgsConstructor
    public static class AssignmentResponseDto{
        private Long assignmentId;
        private String assignmentContent;

        public AssignmentResponseDto(Assignment assignment){
            this.assignmentId = assignment.getAssignmentId();
            this.assignmentContent = assignment.getAssignmentContent();
        }
    }

    @Data
    @NoArgsConstructor
    public static class AssignmentCompleteDto{

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
        private LocalDate completeDate;
    }
}
