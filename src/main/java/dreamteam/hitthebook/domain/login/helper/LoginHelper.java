package dreamteam.hitthebook.domain.login.helper;

import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
import dreamteam.hitthebook.domain.login.entity.ApiToken;
import dreamteam.hitthebook.domain.login.repository.TokenRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

import static dreamteam.hitthebook.domain.login.dto.LoginDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHelper {
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final AuthCodeHelper authCodeHelper;
    private final JwtTokenProvider jwtTokenProvider;

    private final SpringTemplateEngine templateEngine;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member findMemberByEmailAndPassword(String emailId, String password){
        Member member = memberRepository.findByEmailId(emailId).orElseThrow(RuntimeException::new);
        if(!authenticatePassword(password, member.getPassword())){
            throw new RuntimeException();
        }
        return member;
    }
    
    public void verifyEmailAvailability(String emailId){ // 이메일이 존재한다면 예외처리
        if(memberRepository.findByEmailId(emailId).isPresent()){throw new RuntimeException();} // 나중에 예외 수정 예정}
    }

    public void checkValidPassword(String password){ // 비밀번호 예외처리 구현예정, 비밀번호 기획 필요함
        Pattern DIGIT_PATTERN = Pattern.compile("\\d");
        Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]");
        Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
        if (password == null || password.length() < 10 || password.length() > 20) {
            throw new RuntimeException();
        }
        if (!DIGIT_PATTERN.matcher(password).find()) {
            throw new RuntimeException();
        }
        if (!LETTER_PATTERN.matcher(password).find()) {
            throw new RuntimeException();
        }
        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            throw new RuntimeException();
        }
    }

    public void checkValidNickname(String nickname){ // 닉네임 예외처리 구현예정, 닉네임 기획 필요함
        Pattern VALID_CHAR_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$");
        if (nickname == null) {
            throw new RuntimeException();
        }

        if (nickname.contains(" ")) {
            throw new RuntimeException();
        }

        byte[] nicknameBytes = nickname.getBytes(StandardCharsets.UTF_8);
        int length = nicknameBytes.length;
        if (length < 6 || length > 30) { // 한글 2자(6바이트) ~ 10자(30바이트) 기준
            throw new RuntimeException();
        }

        if (!VALID_CHAR_PATTERN.matcher(nickname).matches()) {
            throw new RuntimeException();
        }
    }

    public ApiToken findRefreshTokenAtDBByToken(String refreshToken){
        return tokenRepository.findByRefreshTokenAndCreatedAtAfter(refreshToken, LocalDateTime.now().minusDays(90)).orElseThrow(RuntimeException::new);
    }

    public void ifExistRefreshTokenDelete(Member member){
        ApiToken originToken = tokenRepository.findByMemberAndCreatedAtAfter(member,LocalDateTime.now().minusDays(90));
        if(originToken != null){
            deleteRefreshToken(originToken);
        }
    }

    public void deleteRefreshToken(ApiToken token){
        tokenRepository.delete(token);
    }

    public void saveRefreshToken(String refreshToken, Member member){
        ApiToken token = new ApiToken(refreshToken, member);
        tokenRepository.save(token);
    }


    public LoginTokenDto toLoginTokenDto(Member member){
        String accessToken = jwtTokenProvider.generateAccessToken(member);
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        saveRefreshToken(refreshToken, member);
        return new LoginTokenDto("success", accessToken, refreshToken);
    }

    public void createNewMember(JoinRequestDto joinRequestDto){
        String emailId = joinRequestDto.emailId;
        String nickname = joinRequestDto.nickname;
        String password = joinRequestDto.password;
        String securePassword = createNewSecurePassword(password);
        Member member = new Member(emailId, securePassword, nickname);
        memberRepository.save(member);
    }

    public boolean authenticatePassword(String rawPassword, String encryptedPassword) { // 사용자 입력 비밀번호와 저장된 암호화된 비밀번호를 비교
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    public String createNewSecurePassword(String password){
        return passwordEncoder.encode(password);
    }

    public void makeAuthCodeMail(String toEmail) { // 이메일 단순 객체 생성 후 전송
        String authCode = authCodeHelper.createAuthCode(toEmail); // 암호생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("mailsystem9983@naver.com");
        message.setSubject("[힛더북] 회원가입 인증번호입니다.");
        message.setText("이메일 인증 코드 : " + authCode);
        try{
            mailSender.send(message);
        }
        catch (MailException e){
            throw new RuntimeException(e); // 이메일 오류 예외처리 추가 예정
        }
    }

    public void makeAuthCodeTemplateMail(String toEmail) {
        try {
            String authCode = authCodeHelper.createAuthCode(toEmail);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom("mailsystem9983@naver.com");
            helper.setSubject("[힛더북] 회원가입 인증번호입니다.");

            Context context = new Context();
            context.setVariable("authCode", authCode);

            String html = templateEngine.process("emailTemplate", context);
            helper.setText(html, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.info("HTML TEMPLATE AUTH CODE ERROR!!");
        }
    }

    public void checkValidateCode(String emailId, String authCode){
        if(!authCodeHelper.validateAuthCode(emailId, authCode)){
            throw new RuntimeException(); // 이메일 인증 오류 예외처리 추가
        }
    }

}
