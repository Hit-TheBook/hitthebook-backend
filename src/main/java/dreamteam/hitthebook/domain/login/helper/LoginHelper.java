package dreamteam.hitthebook.domain.login.helper;

import dreamteam.hitthebook.common.exception.*;
import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
import dreamteam.hitthebook.domain.login.entity.ApiToken;
import dreamteam.hitthebook.domain.login.repository.ApiTokenRepository;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
    private final ApiTokenRepository apiTokenRepository;
    private final JavaMailSender mailSender;
    private final AuthCodeHelper authCodeHelper;
    private final JwtTokenProvider jwtTokenProvider;

    private final SpringTemplateEngine templateEngine;

    @Value("${jwt.refreshExpirationDay}")
    private Long refreshExpiration;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Member findMemberByEmailAndPassword(String emailId, String password){ // id와 비밀번호로 멤버 검색 bcrypt의 특징때문에 해당 로직 사용
        Member member = memberRepository.findByEmailId(emailId).orElseThrow(IDNotFoundException::new);
        if(!authenticatePassword(password, member.getPassword())){
            throw new PasswordNotFoundException();
        }
        return member;
    }
    
    public void verifyEmailAvailability(String emailId){ // 이메일이 존재한다면 예외처리
        if(memberRepository.findByEmailId(emailId).isPresent()){throw new DuplicateIDException();}
    }

    public void verifyEmailExits(String emailId){
        if(memberRepository.findByEmailId(emailId).isEmpty()){throw new DuplicateIDException();}
    }

    public void checkValidPassword(String password){ // 비밀번호 예외처리 구현예정, 비밀번호 기획 필요함
        Pattern DIGIT_PATTERN = Pattern.compile("\\d");
        Pattern LETTER_PATTERN = Pattern.compile("[a-zA-Z]");
        Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9]");
        if (password == null || password.length() < 10 || password.length() > 20) {
            throw new InvalidFormatException();
        }
        if (!DIGIT_PATTERN.matcher(password).find()) {
            throw new InvalidFormatException();
        }
        if (!LETTER_PATTERN.matcher(password).find()) {
            throw new InvalidFormatException();
        }
        if (!SPECIAL_CHAR_PATTERN.matcher(password).find()) {
            throw new InvalidFormatException();
        }
    }

    public void checkValidNickname(String nickname){
        Pattern VALID_CHAR_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$");
        if (nickname == null) {
            throw new InvalidFormatException();
        }

        if (nickname.contains(" ")) {
            throw new InvalidFormatException();
        }

        byte[] nicknameBytes = nickname.getBytes(StandardCharsets.UTF_8);
        int length = nicknameBytes.length;
        if (length < 6 || length > 30) { // 한글 2자(6바이트) ~ 10자(30바이트) 기준
            throw new InvalidFormatException();
        }

        if (!VALID_CHAR_PATTERN.matcher(nickname).matches()) {
            throw new InvalidFormatException();
        }
    }

    public ApiToken findRefreshTokenAtDBByToken(String refreshToken){ // 리프레시토큰을 이용하여 DB에서 유효기간 내의 리프레시토큰 검색, 만료 기한 데이터는 일단 임시
        return apiTokenRepository.findByRefreshTokenAndCreatedAtAfter(refreshToken, LocalDateTime.now().minusDays(refreshExpiration)).orElseThrow(InvalidTokenException::new);
    }

    public void ifExistRefreshTokenDelete(Member member){ //리프레시 토큰이 존재한다면, 제거해서 갱신을 대비
        ApiToken originToken = apiTokenRepository.findByMemberAndCreatedAtAfter(member,LocalDateTime.now().minusDays(refreshExpiration));
        if(originToken != null){
            deleteRefreshToken(originToken);
        }
    }

    public void deleteRefreshToken(ApiToken token){ // 리프레시토큰을 제거
        apiTokenRepository.delete(token);
    } // 리프레시토큰 제거

    public void saveRefreshToken(String refreshToken, Member member){ // 리프레시토큰을 저장
        ApiToken token = new ApiToken(refreshToken, member);
        apiTokenRepository.save(token);
    }

    public LoginTokenDto toTempTokenDto(Member member){ // 임시 토큰 발급 관련
        String accessToken = jwtTokenProvider.generateEternalToken(member);
        String refreshToken = jwtTokenProvider.generateEternalToken(member);
        saveRefreshToken(refreshToken, member);
        return new LoginTokenDto("TempToken issued successfully", accessToken, refreshToken);
    }


    public LoginTokenDto toLoginTokenDto(Member member){ // 해당 멤버의 토큰을 발급해줌
        String accessToken = jwtTokenProvider.generateAccessToken(member);
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        saveRefreshToken(refreshToken, member);
        return new LoginTokenDto("UserToken issued successfully", accessToken, refreshToken);
    }

    public void createNewMember(JoinRequestDto joinRequestDto){ // 새로운 멤버 생성
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

    public String createNewSecurePassword(String password){ // bcrypt방법으로 암호화하여 비밀번호 생성
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
            throw new EmailSendFailureException();
        }
    }

    public void makeAuthCodeTemplateMail(String toEmail) { // 이메일 HTML 객체 생성 후 전송
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
            throw new EmailSendFailureException();
        }
    }

    public void checkValidateCode(String emailId, String authCode){ // 레디스에 저장된 인증번호와 일치한다면, 인증완료
        if(!authCodeHelper.validateAuthCode(emailId, authCode)){
            throw new EmailAuthenticationException();
        }
    }

}
