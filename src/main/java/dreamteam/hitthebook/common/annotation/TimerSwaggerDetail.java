package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class TimerSwaggerDetail {
    @Operation(summary = "타이머 과목 추가하기", description = "타이머 과목명만을 이용해서 추가한다." +
            "body에 내용을 담을 필요는 없고 그냥 pathvariable에만 담아주면 된다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AddTimerSubjectDetail{
    }

    @Operation(summary = "타이머 종료 api", description = "타이머가 종료됐을 때 서버로 전송, 타이머 히스토리 데이터 저장 및 " +
            "타이머 과목별 데이터에 데이터를 갱신한다. 수정 및 생성이지만 요청의 의미가 큰것으로 판단되어 post사용")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AddTimerHistoryDetail{
    }

    @Operation(summary = "타이머 과목 삭제하기", description = "타이머 과목을 삭제한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DeleteTimerSubjectDetail{
    }

    @Operation(summary = "타이머 과목 수정하기", description = "타이머 과목명을 수정한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ModifyTimerSubjectDetail{
    }

    @Operation(summary = "타이머 과목 조회하기", description = "타이머 목록을 조회한다. 따로 날짜 선택기능은 없는 것으로 보아 오늘 데이터만 조회한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FindTimerListDetail{
    }

    @Operation(summary = "타이머 관련 데이터 조회하기", description = "오늘의 타이머 누적시간, 누적 획득포인트, 유저의 레벨을 조회한다." +
            "유저의 레벨을 조회하는 이유는 레벨업 시 갱신해야하는 기능을 넣어줄 때 필요할까봐 추가하였음")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetTimerInfoDetail{
    }

    @Operation(summary = "일간 타이머 총 누적시간 조회하기", description = "선택한 날짜에 대하여 일주일간의 총 누적시간을 조회한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetTotalDailyTimerStatisticsDetail{
    }

    @Operation(summary = "일간 타이머 과목별 누적시간 조회하기", description = "선택한 날짜에 대하여 일주일간의 과목별 누적시간을 조회한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetSubjectDailyTimerStatisticsDetail{
    }

    @Operation(summary = "주간 타이머 총 누적시간 조회하기", description = "선택한 날짜에 대하여 4주간의 총 누적시간을 조회한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetTotalWeeklyTimerStatisticsDetail{
    }

    @Operation(summary = "주간 타이머 과목별 누적시간 조회하기", description = "선택한 날짜에 대하여 4주간의 과목별 누적시간을 조회한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface GetSubjectWeeklyTimerStatisticsDetail{
    }
}
