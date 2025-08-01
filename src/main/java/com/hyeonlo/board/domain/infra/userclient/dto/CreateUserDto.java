package com.hyeonlo.board.domain.infra.userclient.dto;

import com.hyeonlo.board.domain.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {

    private String loginId;
    private String password;
    private String userName;
    private String email;
    private UserRole userRole;

}