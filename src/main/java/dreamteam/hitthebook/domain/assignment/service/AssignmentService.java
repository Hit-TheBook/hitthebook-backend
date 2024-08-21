package dreamteam.hitthebook.domain.assignment.service;


import dreamteam.hitthebook.domain.assignment.entity.Assignment;
import dreamteam.hitthebook.domain.assignment.helper.AssignmentHelper;
import dreamteam.hitthebook.domain.assignment.repository.AssignmentRepository;
import dreamteam.hitthebook.domain.assignmentevent.entity.AssignmentEvent;
import dreamteam.hitthebook.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static dreamteam.hitthebook.domain.assignment.dto.AssignmentDto.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentHelper assignmentHelper;
    private final AssignmentRepository assignmentRepository;

    public AssignmentResponseDto createAssignment(AssignmentRequestDto assignmentRequestDto, String emailId){
        Member member = assignmentHelper.findMemberByEmailId(emailId);
        Assignment assignment = Assignment.createByRequestDto(assignmentRequestDto,member);
        Assignment savedAssignment = assignmentRepository.save(assignment);
        assignmentHelper.createAssignmentEvent(savedAssignment, assignmentRequestDto.getAssignmentStartAt(),assignmentRequestDto.getAssignmentEndAt(),assignmentRequestDto.getDayOfWeekEnumList());
        return new AssignmentResponseDto(savedAssignment);
    }

    public void setAssignmentComplete(AssignmentCompleteDto assignmentCompleteDto, Long assignmentId, String emailId){
        Member member = assignmentHelper.findMemberByEmailId(emailId);
        Assignment originAssignment = assignmentHelper.findAssignmentByAssignmentId(assignmentId);
        assignmentHelper.checkAssignmentEditPermission(originAssignment,member);

        AssignmentEvent originAssignmentEvent = assignmentHelper.findAssignmentEventByAssignmentIdAndDate(assignmentCompleteDto,assignmentId);
        assignmentHelper.updateAssignmentComplete(originAssignmentEvent);
    }

}
