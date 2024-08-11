package dreamteam.hitthebook.configuration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class DurationConfig implements Converter<String, Duration> {

    @Override
    public Duration convert(String source) {

        String[] parts = source.split(":");
        long hours = Long.parseLong(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);
        return Duration.ofHours(hours).plusMinutes(minutes).plusSeconds(seconds);
    }

}