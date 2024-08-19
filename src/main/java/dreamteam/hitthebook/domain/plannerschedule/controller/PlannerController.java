package dreamteam.hitthebook.domain.plannerschedule.controller;


import dreamteam.hitthebook.common.annotation.SwaggerToken;
import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenHelper;
import dreamteam.hitthebook.domain.dday.dto.DdayDto;
import dreamteam.hitthebook.domain.plannerschedule.service.PlannerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/planner")
@RequiredArgsConstructor
public class PlannerController {
    private final JwtTokenHelper jwtTokenHelper;
    private final PlannerService plannerService;

    @PostMapping("")
    @SwaggerToken
    public CommonResponseDto scheduleCreate(HttpServletRequest request){
        String emailId = (String) jwtTokenHelper.getMemberEmailIdByToken(request);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }
}
