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

    //TimerController
    @Operation(summary = "타이머 추가(시작)", description = "과목명에 따른 타이머를 추가한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerStartDetail {
    }

    @Operation(summary = "타이머 종료", description = "타이머 종료시 공부 시간을 저장한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerEndDetail {
    }

    @Operation(summary = "타이머 삭제", description = "타이머를 삭제한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerDeleteDetail {
    }

    @Operation(summary = "타이머의 과목명 수정", description = "해당 타이머의 과목명을 수정한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerNameModifyDetail {
    }

    @Operation(summary = "날짜에 따른 타이머 기록 가져오기", description = "입력 받은 날짜에 따른 타이머 기록을 가져온다. 기준은 데이터가 마지막으로 업데이트 된 날짜.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerListWithDateDetail {
    }

}