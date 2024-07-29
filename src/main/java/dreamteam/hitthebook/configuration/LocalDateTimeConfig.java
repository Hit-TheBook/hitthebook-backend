package dreamteam.hitthebook.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeConfig implements WebMvcConfigurer {

    // 스웨거 테스트시에 localdatetime 변환 오류가 떠서 400이 뜨게됨
    // 그 부분을 해결하려고 구현한 config
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, LocalDateTime.class, source -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            return LocalDateTime.parse(source, formatter);
        });
    }
}