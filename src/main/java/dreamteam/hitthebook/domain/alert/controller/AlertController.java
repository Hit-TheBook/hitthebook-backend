package dreamteam.hitthebook.domain.alert.controller;

import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.alert.service.AlertService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static dreamteam.hitthebook.domain.alert.dto.AlertDto.*;

@Slf4j
@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {
    private final JwtTokenHelper jwtTokenHelper;
    private final AlertService alertService;

    @GetMapping
    public AlertResponseDto alertListFind(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return alertService.findAlertList(emailId);
    }
}
