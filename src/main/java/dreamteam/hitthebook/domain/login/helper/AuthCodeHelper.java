package dreamteam.hitthebook.domain.login.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AuthCodeHelper {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;
    private static final long EXPIRATION_TIME = 5;

    private final StringRedisTemplate redisTemplate;

    public String createAuthCode(String key) {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        String authCode = code.toString();

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, authCode, EXPIRATION_TIME, TimeUnit.MINUTES);

        return authCode;
    }

    public boolean validateAuthCode(String key, String authCode) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String storedCode = ops.get(key);

        if (authCode.equals(storedCode)) {
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }

    private String generateKey(String email) {
        return "authCode:" + email;
    }
}
