package dreamteam.hitthebook.domain.timer.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // HH:mm:ss 형식의 문자열을 읽어옴
        String timeString = p.getText();
        LocalTime time = LocalTime.parse(timeString); // HH:mm:ss 형태를 LocalTime으로 변환
        return LocalDateTime.of(LocalDate.now(), time); // 오늘 날짜와 결합하여 LocalDateTime 반환
    }
}