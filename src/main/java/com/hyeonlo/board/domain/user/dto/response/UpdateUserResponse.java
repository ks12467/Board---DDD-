package com.hyeonlo.board.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UpdateUserResponse {

    private final String message;

    private UpdateUserResponse(String message) {
        this.message = message;
    }

    public static UpdateUserResponse of(String message) {
        return new UpdateUserResponse(
                message
        );
    }
}
