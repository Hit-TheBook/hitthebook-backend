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

    public LoginTokenDto makeTokenService(LoginRequestDto loginRequestDto) {
        Member member = loginHelper.findMemberByEmailAndPassword(loginRequestDto.emailId, loginRequestDto.password);
        return new LoginTokenDto("successful login", jwtTokenProvider.generateAccessToken(member), jwtTokenProvider.generateRefreshToken(member));
    }

    public CommonResponseDto joinMember(JoinRequestDto joinRequestDto){
        //회원가입 로직
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    public CommonResponseDto authenticateEmail(EmailRequestDto emailRequestDto){
        loginHelper.verifyEmailAvailability(emailRequestDto.emailId);
//        loginHelper.sendAuthCodeMail(loginHelper.makeAuthCodeMail(emailRequestDto.emailId)); 기존에 메세지만 보내던 헬퍼코드
        loginHelper.makeAuthCodeTemplateMail(emailRequestDto.emailId);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }

    public CommonResponseDto verifyAuthenticationCode(AuthCodeRequestDto authCodeRequestDto){
        loginHelper.checkValidateCode(authCodeRequestDto.emailId, authCodeRequestDto.authCode);
        return CommonResponseDto.builder()
                .message("successful")
                .build();
    }


}
