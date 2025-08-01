package com.hyeonlo.board.domain.auth.dto.response;

import com.hyeonlo.board.domain.auth.dto.request.LoginRequest;
import lombok.Getter;

@Getter
public class LoginResponse {

    private String message;
    private String token;

    private LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public static LoginResponse of(String message, String token) {
        return new LoginResponse(
                message,
                token
        );
    }
}
