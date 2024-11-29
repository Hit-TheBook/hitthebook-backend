package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class PlannerSwaggerDetail {
    //PlannerController
    @Operation(summary = "플래너에 일정 추가하기", description = "플래너에 일정들을 추가한다." +
            "겹치는 일정이 있으면 배제한다")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerAddScheduleDetail{
    }

    @Operation(summary = "일별 플래너 리스트 불러오기", description = "일정의 종류를 구분한다, 또한 날짜를 파라미터로 입력한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerFindScheduleDetail{
    }

    @Operation(summary = "일별 플래너 리스트 전체 불러오기(유형구분없이)", description = "일정의 종류를 구분하지 않는다, 또한 날짜를 파라미터로 입력한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerAllFindScheduleDetail{
    }

    @Operation(summary = "스케줄에 피드백 추가하기", description = "스케쥴에 피드백을 추가한다. 예외처리검사는 본인인지와" +
            "해당 스케쥴에 대한 uri가 일치하는지 이루어진다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerAddFeedbackDetail{
    }

    @Operation(summary = "스케줄 연기하기", description = "스케줄에 피드백을 추가할 때 연기를 골랐다면 날짜를 연기시키는 해당 api를 사용한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerPostponeScheduleDetail{
    }

    @Operation(summary = "오늘의 총평 추가하기", description = "사용자가 오늘의 총평을 건드리면 바로 빈 데이터를 생성을 시킨다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerAddDailyReviewDetail{
    }

    @Operation(summary = "오늘의 총평 수정하기", description = "사용자가 오늘의 총평에 키보드 입력을 마칠때마다 수정한다." +
            "원래 하려한 방법은 사용자가 키보드 입력을 할때마다 0.3초마다 api를 보내는 방법이지만, 그렇게하면 서버비용이 너무 많이 나온다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerModifyDailyReviewDetail{
    }

    @Operation(summary = "오늘의 총평 가져오기", description = "저장된 오늘의 총평이 있다면 가져온다. 없을 수도 있다." +
            "사용자가 오늘의 총평을 작성을 시도하면 생기기 때문이다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PlannerGetDailyReviewDetail{
    }
}
