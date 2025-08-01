package com.hyeonlo.board.global.apipayload.status;

import com.hyeonlo.board.global.apipayload.BaseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessStatus implements BaseCode {

    OK(HttpStatus.OK.value(), "S000", "요청이 성공적으로 처리되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED.value(), "S001", "요청이 성공적으로 처리되었습니다."),
    CREATE_USER(HttpStatus.CREATED.value(), "S002", "회원가입이 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK.value(), "S003", "로그인이 성공하였습니다."),
    UPDATE_SUCCESS(HttpStatus.ACCEPTED.value(), "S004", "회원정보 수정이 완료되었습니다."),
    DELETE_USER(HttpStatus.ACCEPTED.value(), "S005", "회원 탈퇴가 정상적으로 처리되었습니다." );

    private final int status;
    private final String code;
    private final String message;

    SuccessStatus(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
