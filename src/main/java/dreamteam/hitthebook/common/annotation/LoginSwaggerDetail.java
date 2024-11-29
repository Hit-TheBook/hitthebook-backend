package dreamteam.hitthebook.common.annotation;

import io.swagger.v3.oas.annotations.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class LoginSwaggerDetail {
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
}
