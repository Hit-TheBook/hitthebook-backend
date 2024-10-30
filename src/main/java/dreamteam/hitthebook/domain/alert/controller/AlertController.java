package dreamteam.hitthebook.domain.alert.controller;

import dreamteam.hitthebook.common.annotation.SwaggerDetail;
import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.alert.dto.AlertDto;
import dreamteam.hitthebook.domain.alert.entity.Alert;
import dreamteam.hitthebook.domain.alert.helper.AlertHelper;
import dreamteam.hitthebook.domain.alert.service.AlertService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dreamteam.hitthebook.common.annotation.SwaggerDetail.*;
import static dreamteam.hitthebook.domain.alert.dto.AlertDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
public class AlertController {
    private final JwtTokenHelper jwtTokenHelper;
    private final AlertService alertService;

    @GetMapping("")
    @SwaggerToken
    //@AlertLoadingDetail
    public CommonResponseDto assignmentAlarmSet(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        alertService.setAssignmentAlarm(emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    @GetMapping("/privateAlarm")
    @SwaggerToken
    //@AlertPrivateDetail
    public AlertListDto assignmentAlarmListFind(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return alertService.findAlertList(emailId);
    }
}
