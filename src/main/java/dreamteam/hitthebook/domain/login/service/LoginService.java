package dreamteam.hitthebook.domain.login.service;

import dreamteam.hitthebook.common.dto.CommonResponseDto;
import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
import dreamteam.hitthebook.domain.login.entity.ApiToken;
import dreamteam.hitthebook.domain.login.helper.LoginHelper;
import dreamteam.hitthebook.domain.login.repository.ApiTokenRepository;
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
    private final ApiTokenRepository tokenRepository;
    private final LoginHelper loginHelper;

    public LoginTokenDto makeTokenService(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmailIdAndPassword(loginRequestDto.emailId, loginRequestDto.password).orElseThrow(ResourceNotFoundException::new);
        loginHelper.ifExistRefreshTokenDelete(member);
        return loginHelper.toTempTokenDto(member);
    }

    public LoginTokenDto loginService(LoginRequestDto loginRequestDto) {
        Member member = loginHelper.findMemberByEmailAndPassword(loginRequestDto.emailId, loginRequestDto.password);
        loginHelper.ifExistRefreshTokenDelete(member);
        return loginHelper.toLoginTokenDto(member);
    }

    public LoginTokenDto issueTokenService(String refreshToken) {
        ApiToken storedRefreshToken = loginHelper.findRefreshTokenAtDBByToken(refreshToken);
        loginHelper.deleteRefreshToken(storedRefreshToken);
        Member member = storedRefreshToken.getMember();
        return loginHelper.toLoginTokenDto(member);
    }

    public CommonResponseDto joinMember(JoinRequestDto joinRequestDto){
        //loginHelper.checkValidPassword(joinRequestDto.password);
        //loginHelper.checkValidNickname(joinRequestDto.nickname); // 닉네임과 패스워드 모두 프론트엔드에서 별도로 검토처리를 함, 편의를 위해서 일단 주석처리
        loginHelper.createNewMember(joinRequestDto);
        return CommonResponseDto.builder()
                .message("Your registration was successful!")
                .build();
    }

    public CommonResponseDto authenticateEmail(EmailRequestDto emailRequestDto){
        loginHelper.verifyEmailAvailability(emailRequestDto.emailId);
//        loginHelper.sendAuthCodeMail(loginHelper.makeAuthCodeMail(emailRequestDto.emailId)); 기존에 메세지만 보내던 헬퍼코드
        loginHelper.makeAuthCodeTemplateMail(emailRequestDto.emailId);
        return CommonResponseDto.builder()
                .message("The verification code has been successfully sent to your email.")
                .build();
    }

    public CommonResponseDto verifyAuthenticationCode(AuthCodeRequestDto authCodeRequestDto){
        loginHelper.checkValidateCode(authCodeRequestDto.emailId, authCodeRequestDto.authCode);
        return CommonResponseDto.builder()
                .message("Email authentication was successful!")
                .build();
    }


}
