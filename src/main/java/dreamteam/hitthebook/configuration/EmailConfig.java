package dreamteam.hitthebook.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

//@Slf4j
//@Configuration
//public class EmailConfig {
//    @Value("${spring.mail.username}")
//    private String username;
//
//    @Value("${spring.mail.password}")
//    private String password;
//
//    @Bean
//    public JavaMailSender javaMailService() {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//
//        javaMailSender.setHost("smtp.naver.com");
//        javaMailSender.setUsername(username);
//        javaMailSender.setPassword(password);
//
//        javaMailSender.setPort(465);
//        javaMailSender.setProtocol("smtps");
//
//        log.info("EmailConfig: Initializing JavaMailSender with username: {}", username);
//
//        Properties mailProperties = getMailProperties();
//        javaMailSender.setJavaMailProperties(mailProperties);
//
//        // Test the mail sender configuration by sending a test email
//        try {
//            log.info("EmailConfig: Testing mail server connection by sending a test email...");
//            sendTestEmail(javaMailSender);
//            log.info("EmailConfig: Mail server connection successful.");
//        } catch (MailException e) {
//            log.error("EmailConfig: Failed to connect to the mail server. Please check your configuration.", e);
//        }
//
//        return javaMailSender;
//    }
//
//    private Properties getMailProperties() {
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", "smtp.naver.com");
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        properties.put("mail.smtp.timeout", "5000");
//        properties.put("mail.smtp.connectiontimeout", "5000");
//        properties.put("mail.transport.protocol", "smtps");  // 프로토콜 설정 추가
//        return properties;
//    }
//
//    private void sendTestEmail(JavaMailSender javaMailSender) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(username);  // 자신에게 테스트 메일을 보냄
//        message.setSubject("Test Email");
//        message.setText("This is a test email to verify mail server connection.");
//
//        javaMailSender.send(message);
//    }
//}