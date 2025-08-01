package com.hyeonlo.board.domain.infra.userclient.dto;

import com.hyeonlo.board.domain.user.enums.UserRole;
import com.hyeonlo.board.domain.user.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String loginId;
    private String password;
    private UserRole userRole;
    private UserStatus userStatus;
}
