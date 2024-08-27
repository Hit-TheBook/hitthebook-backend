package dreamteam.hitthebook.domain.assignment.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;

import dreamteam.hitthebook.domain.assignment.service.AssignmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static dreamteam.hitthebook.common.annotation.SwaggerDetail.*;
import static dreamteam.hitthebook.domain.assignment.dto.AssignmentDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {
    private final JwtTokenHelper jwtTokenHelper;
    private final AssignmentService assignmentService;

    @PostMapping("")
    @SwaggerToken
    @AssignmentRegisterDetail
    public AssignmentResponseDto assignmentCreate(HttpServletRequest request, AssignmentRequestDto assignmentRequestDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return assignmentService.createAssignment(assignmentRequestDto,emailId);
    }

    @PatchMapping("/{assignmentId}")
    @SwaggerToken
    @AssignmentCompleteDetail
    public CommonResponseDto assignmentCompleteSet(@PathVariable(name = "assignmentId")Long assignmentId, HttpServletRequest request, AssignmentCompleteDto assignmentCompleteDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        assignmentService.setAssignmentComplete(assignmentCompleteDto,assignmentId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @DeleteMapping("/{assignmentId}")
    @SwaggerToken
    @AssignmentDeleteDetail
    public CommonResponseDto assignmentDelete(@PathVariable(name = "assignmentId")Long assignmentId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        assignmentService.deleteAssignment(assignmentId,emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/list")
    @SwaggerToken
    @AssignmentListWithDateDetail
    public AssignmentListDto assignmentListFind(HttpServletRequest request, AssignmentDateDto assignmentDateDto){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return assignmentService.findAssignmentList(emailId,assignmentDateDto);
    }

    @PostMapping("/{assignmentId}")
    @SwaggerToken
    @AssignmentStateDetail
    public AssignmentEventListDto assignmentEventListFind(@PathVariable(name = "assignmentId")Long assignmentId, HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return assignmentService.findAssignmentEventList(assignmentId,emailId);
    }
}
