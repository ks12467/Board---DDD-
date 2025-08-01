package com.hyeonlo.board.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long userId;
    private final String loginId;
    private final String userName;
    private final String email;

    private UserResponse(Long userId, String loginId, String userName, String email) {
        this.userId = userId;
        this.loginId = loginId;
        this.userName = userName;
        this.email = email;
    }

    public static UserResponse of(Long userId, String loginId, String userName, String email) {
        return new UserResponse(
                userId,
                loginId,
                userName,
                email
        );
    }
}
