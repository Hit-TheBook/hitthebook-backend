package dreamteam.hitthebook.domain.member.helper;

import dreamteam.hitthebook.common.exception.*;
import dreamteam.hitthebook.common.jwt.JwtTokenProvider;
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

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

import static dreamteam.hitthebook.domain.member.dto.LoginDto.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginHelper {
    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;
    private final AuthCodeHelper authCodeHelper;
    private final JwtTokenProvider jwtTokenProvider;

    private final SpringTemplateEngine templateEngine;

    @Value("${AESKey.secret}")
    private String secretBase64;

    public String findOriginPassword(String encryptedPassword) {
        try {
            // Base64로 인코딩된 키를 디코딩
            byte[] keyBytes = Base64.getDecoder().decode(secretBase64);
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);

            // IV는 암호화된 데이터의 첫 16바이트
            byte[] iv = new byte[16];
            System.arraycopy(encryptedBytes, 0, iv, 0, 16);

            // 암호화된 데이터 (IV 이후)
            byte[] cipherBytes = new byte[encryptedBytes.length - 16];
            System.arraycopy(encryptedBytes, 16, cipherBytes, 0, cipherBytes.length);

            // AES 키 및 IV 설정
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            // AES 복호화 객체 생성
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            // 복호화 수행
            byte[] decryptedBytes = cipher.doFinal(cipherBytes);

            return new String(decryptedBytes, "UTF-8");

        } catch (Exception e) {
            throw new PasswordNotFoundException();
        }
    }

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

    // emailId를 기반으로 멤버 검색
    public Member findMemberByEmailId(String emailId){
        return memberRepository.findByEmailId(emailId).orElseThrow(ResourceNotFoundException::new);
    }

    // 이메일이 존재한다면 예외처리(회원가입시에 진행하므로 중복되는 이메일을 검사)
    public void verifyEmailAvailability(String emailId){
        if(memberRepository.findByEmailId(emailId).isPresent()){throw new DuplicateIDException();}
    }

    // 존재하지 않는 이메일이라면 예외처리 (비밀번호 찾기에 이용하기 때문에 존재하는 이메일만 인증번호 발송을 진행함)
    public void verifyEmailExits(String emailId){
        if(memberRepository.findByEmailId(emailId).isEmpty()){throw new ResourceNotFoundException();}
    }

    // 8자~16자의 비밀번호, 소문자/대문자/숫자/특수문자 중에서 2가지 이상을 필요로 함
    public void checkValidPassword(String password){
        Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]"); // 대문자 패턴
        Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]"); // 소문자 패턴
        Pattern DIGIT_PATTERN = Pattern.compile("\\d"); // 숫자 패턴
        Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[^a-zA-Z0-9]"); // 특수문자 패턴

        // 비밀번호가 null이거나 길이가 8자리 미만 또는 16자리 초과면 예외 발생
        if (password == null || password.length() < 8 || password.length() > 16) {
            throw new InvalidFormatException();
        }

        // 각 패턴에 맞는 문자가 포함되어 있는지 확인
        boolean hasUppercase = UPPERCASE_PATTERN.matcher(password).find();
        boolean hasLowercase = LOWERCASE_PATTERN.matcher(password).find();
        boolean hasDigit = DIGIT_PATTERN.matcher(password).find();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(password).find();

        // 최소 2종류 이상의 문자가 포함되어 있는지 확인
        int validCategoryCount = 0;
        if (hasUppercase) validCategoryCount++;
        if (hasLowercase) validCategoryCount++;
        if (hasDigit) validCategoryCount++;
        if (hasSpecialChar) validCategoryCount++;

        // 두 가지 이상의 종류가 포함되지 않았다면 예외 발생
        if (validCategoryCount < 2) {
            throw new InvalidFormatException();
        }
    }

    // 유효한 닉네임인지 검사 2자~6자인데 한글 바이트 수가 3바이트이므로 바이트수로 검사하였음, 특수문자 불가능, 공백 불가능
    public void checkValidNickname(String nickname){
        Pattern VALID_CHAR_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$"); // 한글과 영어 숫자만 가능
        if (nickname == null) { // null값 불가능
            throw new InvalidFormatException();
        }

        if (nickname.contains(" ")) { // 공백 불가능
            throw new InvalidFormatException();
        }

        byte[] nicknameBytes = nickname.getBytes(StandardCharsets.UTF_8);
        int length = nicknameBytes.length;
        if (length < 6 || length > 18) { // 한글 2자(6바이트) ~ 6자(18바이트) 기준 2자~6자
            throw new InvalidFormatException();
        }

        if (!VALID_CHAR_PATTERN.matcher(nickname).matches()) { // 한글 영어 숫자가 아닌 경우 예외처리
            throw new InvalidFormatException();
        }
    }

    // 유효한 토큰인지 검증
    public void checkVerifyToken(String token){
        if(!jwtTokenProvider.validateToken(token)){
            throw new InvalidTokenException();
        }
    }

    // 리프레시 토큰으로 멤버 검색
    public Member findMemberByRefreshToken(String refreshToken){
        String emailId = jwtTokenProvider.getEmailIdFromJWT(refreshToken);
        return memberRepository.findByEmailId(emailId).orElseThrow(IDNotFoundException::new);
    }

    // 임시 토큰 발급 관련
    public LoginTokenDto toTempTokenDto(Member member){
        String accessToken = jwtTokenProvider.generateEternalToken(member);
        String refreshToken = jwtTokenProvider.generateEternalToken(member);
        return new LoginTokenDto("TempToken issued successfully", accessToken, refreshToken);
    }

    // 해당 멤버의 토큰을 발급해줌
    public LoginTokenDto toLoginTokenDto(Member member){
        String accessToken = jwtTokenProvider.generateAccessToken(member);
        String refreshToken = jwtTokenProvider.generateRefreshToken(member);
        return new LoginTokenDto("UserToken issued successfully", accessToken, refreshToken);
    }

    // 새로운 멤버 생성
    public void createNewMember(JoinRequestDto joinRequestDto){
        String emailId = joinRequestDto.emailId;
        String nickname = joinRequestDto.nickname;
        String password = joinRequestDto.password;
        String securePassword = createNewSecurePassword(password);
        Member member = new Member(emailId, securePassword, nickname);
        memberRepository.save(member);
    }

    // 사용자 입력 비밀번호와 저장된 암호화된 비밀번호를 비교
    public boolean authenticatePassword(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }

    // bcrypt방법으로 암호화하여 비밀번호 생성
    public String createNewSecurePassword(String password){
        return passwordEncoder.encode(password);
    }

    // 이메일 단순 객체 생성 후 전송
    public void makeAuthCodeMail(String toEmail) {
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

    // 이메일 HTML 객체 생성 후 전송
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
            throw new EmailSendFailureException();
        }
    }

    // 레디스에 저장된 인증번호와 일치한다면, 인증완료
    public void checkValidateCode(String emailId, String authCode){
        if(!authCodeHelper.validateAuthCode(emailId, authCode)){
            throw new EmailAuthenticationException();
        }
    }

    // 패스워드가 현재 패스워드와 일치한다면 예외처리(새로운 비밀번호로만 변경가능)
    public void checkPasswordMatch(Member member, String password){
        if(authenticatePassword(password, member.getPassword())){
            throw new RuntimeException();
        }
    }

    // 비밀번호 변경하는 로직
    public void changePassword(Member member, String newPassword){
        String securePassword = createNewSecurePassword(newPassword);
        member.setPassword(securePassword);
        memberRepository.save(member);
    }

}
