package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SwaggerDetail {
    //AssignmentController
    @Operation(summary = "개인 미션 추가", description = "시작 ~ 종료 날짜와 지정 요일, 개인 미션 내용을 받아 미션을 추가한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AssignmentRegisterDetail {
    }

    @Operation(summary = "개인 미션 완료", description = "내가 완료한 날짜를 체크한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AssignmentCompleteDetail {
    }

    @Operation(summary = "개인 미션 삭제", description = "해당 미션에 대한 엔티티와 그에 따른 이벤트 엡티티 모두를 삭제한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AssignmentDeleteDetail {
    }

    @Operation(summary = "날짜에 따른 미션 목록 불러오기", description = "날짜에 따른 미션 목록을 불러온다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AssignmentListWithDateDetail {
    }

    @Operation(summary = "해당 미션의 수행할 날짜들과 완료여부 불러오기", description = "해당 미션에 따른 미션 상태를 불러온다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AssignmentStateDetail {
    }



}