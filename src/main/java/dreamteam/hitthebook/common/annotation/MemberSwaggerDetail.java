package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MemberSwaggerDetail {
    @Operation(summary = "닉네임 중복확인 검사하기", description = "존재하지 않는 닉네임이라면 200이다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CheckNicknameMatchDetail {
    }
}
