package dreamteam.hitthebook.domain.member.service;

import dreamteam.hitthebook.common.exception.ResourceNotFoundException;
import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
import dreamteam.hitthebook.domain.member.helper.LoginHelper;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static dreamteam.hitthebook.domain.member.dto.LoginDto.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {
    private final MemberRepository memberRepository;
    private final LoginHelper loginHelper;

    public LoginTokenDto makeTokenService() {
        Member member = memberRepository.findByEmailId("test3@example.com").orElseThrow(ResourceNotFoundException::new);
        return loginHelper.toTempTokenDto(member);
    }

    public LoginTokenDto loginService(LoginRequestDto loginRequestDto) {
        Member member = loginHelper.findMemberByEmailAndPassword(loginRequestDto.emailId, loginRequestDto.password);
        return loginHelper.toLoginTokenDto(member);
    }

    public LoginTokenDto issueTokenService(String refreshToken) {
        loginHelper.checkVerifyToken(refreshToken);
        return loginHelper.toLoginTokenDto(loginHelper.findMemberByRefreshToken(refreshToken));
    }

    public void joinMember(JoinRequestDto joinRequestDto){
        loginHelper.checkValidPassword(joinRequestDto.password); // 비밀번호 형식 검사
        loginHelper.checkValidNickname(joinRequestDto.nickname); // 닉네임 형식 검사
        loginHelper.verifyEmailAvailability(joinRequestDto.emailId); // 중복된 아이디인지 다시 검사
        loginHelper.createNewMember(joinRequestDto);
    }

    public void authenticateEmail(EmailRequestDto emailRequestDto){
        loginHelper.verifyEmailAvailability(emailRequestDto.emailId);
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
