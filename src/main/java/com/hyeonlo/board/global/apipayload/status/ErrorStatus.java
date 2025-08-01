package com.hyeonlo.board.global.apipayload.status;

import com.hyeonlo.board.global.apipayload.BaseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus implements BaseCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "E001", "해당 유저를 찾을 수 없습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "E002", "요청 값이 옳바르지 않습니다." ),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "E999", "의도하지 않은 오류가 발생하였습니다."),
    SC_UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "E003", "인증된 회원이 아닙니다."),
    SC_BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "E004", "요청값이 올바르지 않습니다." ),
    ALREADY_EMAIL(HttpStatus.BAD_REQUEST.value(), "E004", "이미 사용중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "E005", "비밀번호가 일치하지 않습니다."),
    ALREADY_DELETE(HttpStatus.BAD_REQUEST.value(), "E006","해당 유저는 탈퇴처리가 되었습니다." );

    private final int status;
    private final String code;
    private final String message;

    ErrorStatus(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
