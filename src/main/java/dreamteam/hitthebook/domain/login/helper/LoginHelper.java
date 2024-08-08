package dreamteam.hitthebook.domain.login.helper;

import dreamteam.hitthebook.domain.login.dto.LoginDto;
import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import org.thymeleaf.spring6.SpringTemplateEngine;

import org.thymeleaf.context.Context;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHelper {
    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final AuthCodeHelper authCodeHelper;

    private final SpringTemplateEngine templateEngine;

    public Member findMemberByEmailAndPassword(String emailId, String password) {
        return memberRepository.findByEmailIdAndPassword(emailId, password).orElseThrow(RuntimeException::new);
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

    public SimpleMailMessage makeAuthCodeMail(String toEmail) { // 이메일 객체 생성
        String authCode = authCodeHelper.createAuthCode(toEmail); // 암호생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("mailsystem9983@naver.com");
        message.setSubject("[힛더북] 회원가입 인증번호입니다.");
        message.setText("이메일 인증 코드 : " + authCode);
        return message;
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

    public void sendAuthCodeMail(SimpleMailMessage message) { // 이메일 전송
        try{
            mailSender.send(message);
        }
        catch (MailException e){
            throw new RuntimeException(e); // 이메일 오류 예외처리 추가 예정
        }
    }

    public void checkValidateCode(String emailId, String authCode){
        if(!authCodeHelper.validateAuthCode(emailId, authCode)){
            throw new RuntimeException(); // 이메일 인증 오류 예외처리 추가
        }
    }


    
}
