package dreamteam.hitthebook.domain.timer.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 총 시간을 계산
        long hours = value.toHours();
        long minutes = value.toMinutesPart();
        long seconds = value.toSecondsPart();

        // "HH:mm:ss" 형식으로 직렬화
        String formattedDuration = String.format("%d:%02d:%02d", hours, minutes, seconds);
        gen.writeString(formattedDuration);
    }
}