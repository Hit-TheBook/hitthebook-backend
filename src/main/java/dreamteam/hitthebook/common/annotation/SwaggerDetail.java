package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SwaggerDetail {
    @Operation(summary = "로그인", description = "유저 조회 후 토큰발급")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LoginDetail {
    }


    //DdayController
    @Operation(summary = "dday 추가", description = "dday를 추가한다, startdate와 enddate는 localdatetime의 형식에 맞게 입력")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayRegisterDetail {
    }

    @Operation(summary = "dday 수정", description = "dday를 수정한다, startdate와 enddate는 localdatetime의 형식에 맞게 입력, 본인만 수정가능")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayUpdateDetail {
    }





}