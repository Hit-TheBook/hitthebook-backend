package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class SwaggerDetail {
    //DdayController
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

    //LoginController
    @Operation(summary = "로그인", description = "유저 조회 후 토큰발급")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LoginDetail {
    }

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

    @Operation(summary = "비밀번호 찾기 시에 이메일로 인증번호 전송", description = "인증번호의 유효기간은 5분이다, 이미 존재하는 아이디라거나 이메일 발송 오류시 오류 발생")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AuthenticateEmailAtForgotDetail {
    }

    @Operation(summary = "인증번호 확인", description = "인증번호의 유효기간은 5분이다, 인증번호를 보내고 유효한 인증번호라면 인증시켜준다." +
            "비밀번호를 잊어버릴때도 같은 api 사용")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AuthenticateEmailCodeDetail {
    }

    @Operation(summary = "회원가입", description = "회원가입 API이다, 닉네임과 패스워드에 대한 검사는 양쪽(프론트엔드와 백엔드 모두)에서 진행된다. " +
            "현재는 검사만 비활성화 해둔 상태" +
            "검사양식은 기획에 따라 수정의 여지가 있다. password는 bcrypt의 암호화방식에 따라서 암호화하여 저장된다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface JoinDetail {
    }

    @Operation(summary = "이전 비밀번호와 같은 비밀번호인지 검사하기", description = "이전 비밀번호와 검사하여 검사성공시 200이다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface CheckPasswordMatchDetail {
    }

    @Operation(summary = "토큰 재발급", description = "리프레시토큰이 유효하다면 새로운 토큰 재발급 및 리프레시토큰 갱신")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ReissueDetail {
    }

    @Operation(summary = "비밀번호 재설정하기", description = "비밀번호가 형식과 일치하면 재설정한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ResetNewPasswordDetail {
    }

    //TimerController
    @Operation(summary = "과목 추가", description = "과목명에 따른 타이머를 추가한다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerStartDetail {
    }

    @Operation(summary = "타이머 시작", description = "타이머의 재생 버튼을 누른 시각이 공부한 날짜가 된다.")
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TimerPlayDetail {
    }

    @Operation(summary = "타이머 종료", description = "타이머 종료 버튼을 누를시 공부 시간을 저장한다.")
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