package dreamteam.hitthebook.domain.assignment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.assignment.enumulation.AssignmentDayOfWeekEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Data
    @NoArgsConstructor
    public static class AssignmentDateDto{

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "UTC")
        private LocalDate assignmentDate;
    }

    @Data
    @NoArgsConstructor
    public static class AssignmentListDto{

        private List<AssignmentContents> assignmentContentsList = new ArrayList<>();
        public AssignmentListDto(List<Assignment> assignmentList){
            for (Assignment assignment : assignmentList){
                this.assignmentContentsList.add(new AssignmentContents(assignment));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class AssignmentContents{

        private Long assignmentId;
        private List<AssignmentDayOfWeekEnum> dayOfWeekEnum;
        private Integer assignmentCompletionRate;
        private String assignmentContent;
        private LocalDate assignmentStartAt;
        private LocalDate assignmentEndAt;

        public AssignmentContents(Assignment assignment){
            this.assignmentId = assignment.getAssignmentId();
            this.dayOfWeekEnum = assignment.getDayOfWeekEnum();
            this.assignmentCompletionRate = assignment.getAssignmentCompletionRate();
            this.assignmentContent = assignment.getAssignmentContent();
            this.assignmentStartAt = assignment.getAssignmentStartAt();
            this.assignmentEndAt = assignment.getAssignmentEndAt();
        }
    }
}
