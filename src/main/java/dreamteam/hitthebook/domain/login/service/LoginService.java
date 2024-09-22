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

    public void joinMember(JoinRequestDto joinRequestDto){
        loginHelper.checkValidPassword(joinRequestDto.password); // 비밀번호 형식 검사
        loginHelper.checkValidNickname(joinRequestDto.nickname); // 닉네임 형식 검사
        loginHelper.verifyEmailAvailability(joinRequestDto.emailId); // 중복된 아이디인지 다시 검사
        loginHelper.createNewMember(joinRequestDto);
    }

    public void authenticateEmail(EmailRequestDto emailRequestDto){
        loginHelper.verifyEmailAvailability(emailRequestDto.emailId);
//        loginHelper.sendAuthCodeMail(loginHelper.makeAuthCodeMail(emailRequestDto.emailId)); 기존에 메세지만 보내던 헬퍼코드
        loginHelper.makeAuthCodeTemplateMail(emailRequestDto.emailId);
    }

    public void authenticateEmailAtForgetPassword(EmailRequestDto emailRequestDto){
        loginHelper.verifyEmailExits(emailRequestDto.emailId);
        loginHelper.makeAuthCodeTemplateMail(emailRequestDto.emailId);
    }

    public void verifyAuthenticationCode(AuthCodeRequestDto authCodeRequestDto){
        loginHelper.checkValidateCode(authCodeRequestDto.emailId, authCodeRequestDto.authCode);
    }

    public void isSamePassword(PasswordDto passwordDto){
        Member member = loginHelper.findMemberByEmailId(passwordDto.emailId);
        loginHelper.checkPasswordMatch(member, passwordDto.password);
    }

    public void resetPassword(PasswordDto passwordDto){
        Member member = loginHelper.findMemberByEmailId(passwordDto.emailId);
        loginHelper.checkValidPassword(passwordDto.password);
        loginHelper.changePassword(member, passwordDto.password);
    }
}
