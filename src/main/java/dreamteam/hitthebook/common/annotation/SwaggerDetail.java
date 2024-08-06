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

    @Operation(summary = "dday 삭제", description = "dday를 삭제한다 본인만 삭제가능")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayDeleteDetail {
    }

    @Operation(summary = "대표dday 등록", description = "대표 dday를 지정한다")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayMakePrimaryDetail {
    }

    @Operation(summary = "대표dday 가져오기", description = "대표 dday를 불러온다")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayGetPrimaryDetail {
    }

    @Operation(summary = "전체 dday 목록 가져오기", description = "대표 dday, 현재 활성화중인 dday, 이미 종료된 dday의 목록을 불러온다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayGetListDetail {
    }

    //LoginController
    @Operation(summary = "임시토큰 발급", description = "테스트를 위한 임시토큰을 발급한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TempTokenDetail {
    }

    @Operation(summary = "이메일로 인증번호 전송", description = "인증번호의 유효기간은 5분이다, 이미 존재하는 아이디라거나 이메일 발송 오류시 오류 발생")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AuthenticateEmailDetail {
    }




}