package com.hyeonlo.board.domain.user.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRole {

    USER("유저"),
    ADMIN("관리자");

    private final String message;

    private UserRole(String message) {
        this.message = message;
    }

    public static UserRole of(String name) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 권한입니다."));
    }
}
