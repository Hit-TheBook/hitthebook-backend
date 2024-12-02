package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AlertSwaggerDetail {
    @Operation(summary = "알림 조회", description = "알림을 전부 조회한다, 귀찮아서 page객체로 안만들었음" +
            "엠블럼 사진을 기재하라는데 이건 안됨 ㅋㅋㅋ 너무 이상")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FindAlertListDetail {
    }
}
