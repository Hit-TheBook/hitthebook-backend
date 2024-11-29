package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class DdaySwaggerDetail {
    @Operation(summary = "dday 추가", description = "dday를 추가한다, " +
            "startdate와 enddate는 localdatetime의 형식에 맞게 입력" +
            "startdate와 enddate로 조건을 검색할 일이 없어서 따로 시분초를 정확히 입력해주어야할 필요는 없다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayRegisterDetail {
    }

    @Operation(summary = "dday 수정", description = "dday를 수정한다, " +
            "startdate와 enddate는 localdatetime의 형식에 맞게 입력, " +
            "본인만 수정가능" +
            "startdate와 enddate로 조건을 검색할 일이 없어서 따로 시분초를 정확히 입력해주어야할 필요는 없다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayUpdateDetail {
    }

    @Operation(summary = "dday 삭제", description = "dday를 삭제한다. 본인만 삭제가능")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayDeleteDetail {
    }

    @Operation(summary = "대표dday 등록", description = "대표 dday를 지정한다" +
            "이미 지정된 대표디데이가 있다면 기존의 대표 디데이를 일반디데이로 바꾸고 새로 대표디데이로 지정한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayMakePrimaryDetail {
    }

    @Operation(summary = "대표dday 가져오기", description = "대표 dday를 불러온다" +
            "이 기능은 나의 스터디에서 대표디데이를 나타내줘야하는데 그 때 쓰이는 api이다." +
            "타입은 NOT_STARTED, IN_PROGRESS, COMPLETED 중의 하나다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayGetPrimaryDetail {
    }

    @Operation(summary = "전체 dday 목록 가져오기", description = "대표 dday, 현재 활성화중인 dday, " +
            "이미 종료된 dday의 목록을 불러온다." +
            "remainingDays는 오늘과 endDate의 차이 그리고 DurationDays는 startDate와 endDate의 차이이다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DdayGetListDetail {
    }
}
