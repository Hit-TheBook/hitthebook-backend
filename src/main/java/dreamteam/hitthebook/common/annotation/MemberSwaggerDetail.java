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

    @Operation(summary = "닉네임 조회하기", description = "유저의 닉네임 조회")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetNicknameDetail {
    }

    @Operation(summary = "닉네임 수정하기", description = "유저의 닉네임 수정")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModifyNicknameDetail {
    }

    @Operation(summary = "레벨 데이터 가져오기", description = "유저의 레벨데이터 포인트 그리고 다음레벨까지의 데이터를 가져온다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetLevelDetail {
    }

    @Operation(summary = "엠블럼 목록 가져오기", description = "유저가 가진 엠블럼 목록과 데이터를 제공한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetEmblemDetail {
    }
}
