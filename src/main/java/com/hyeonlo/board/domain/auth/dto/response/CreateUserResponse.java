package com.hyeonlo.board.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class CreateUserResponse {

    private final String message;

    private CreateUserResponse(String message) {
        this.message = message;
    }

    public static CreateUserResponse of(String message) {
        return new CreateUserResponse(
                message
        );
    }
}
