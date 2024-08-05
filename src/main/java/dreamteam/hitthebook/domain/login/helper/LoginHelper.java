package dreamteam.hitthebook.domain.login.helper;

import dreamteam.hitthebook.domain.member.entity.Member;
import dreamteam.hitthebook.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHelper {
    private final MemberRepository memberRepository;

    private final JavaMailSender mailSender;
    
    public void verifyEmailAvailability(String emailId){ // 이메일이 존재한다면 예외처리
        if(memberRepository.findByEmailId(emailId).isPresent()){throw new RuntimeException();} // 나중에 예외 수정 예정}
    }

    public String createAuthCode(){
        String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int CODE_LENGTH = 6;
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public SimpleMailMessage makeAuthCodeMail(String toEmail) {
        String authCode = createAuthCode();
        log.info("★★★★★★★★★★★★★★★★★★★★★★★★★★★★{}{}",toEmail,authCode);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom("mailsystem9983@naver.com");
        message.setSubject("Your Authentication Code");
        message.setText("Your authentication code is: " + authCode);
        return message;
    }

    public void sendAuthCodeMail(SimpleMailMessage message) {
        try{
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@{}",message);
            mailSender.send(message);
        }
        catch (MailException e){
            log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ERROR");
            throw new RuntimeException(e);
        }
    }


    
}
