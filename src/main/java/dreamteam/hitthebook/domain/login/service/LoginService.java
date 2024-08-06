package dreamteam.hitthebook.domain.login.service;

import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
import dreamteam.hitthebook.domain.login.helper.LoginHelper;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dreamteam.hitthebook.domain.login.dto.LoginDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginHelper loginHelper;

    public LoginTokenDto makeTokenService(LoginRequsetDto loginRequsetDto) {
        Member member = memberRepository.findByEmailIdAndPassword(loginRequsetDto.emailId, loginRequsetDto.password);
        if(member == null) {
            throw new RuntimeException();
        }
        String accessToken = jwtTokenProvider.generateAccessToken(member);
        String refreshToken = jwtTokenProvider.generateAccessToken(member);
        return new LoginTokenDto("successful login", accessToken, refreshToken);
    }

    public CommonResponseDto authenticateEmail(String emailId){
        loginHelper.verifyEmailAvailability(emailId);
        loginHelper.sendAuthCodeMail(loginHelper.makeAuthCodeMail(emailId));

        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    public CommonResponseDto verifyAuthenticationCode(String emailId, String authCode){
        loginHelper.checkValidateCode(emailId, authCode);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }


}
