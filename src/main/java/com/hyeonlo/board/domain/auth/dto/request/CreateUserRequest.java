package com.hyeonlo.board.domain.auth.dto.request;

import com.hyeonlo.board.domain.infra.userclient.dto.CreateUserDto;
import com.hyeonlo.board.domain.infra.userclient.dto.UserDto;
import com.hyeonlo.board.domain.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "로그인 아이디 입력은 필수입니다.")
    private String loginId;
    @NotNull@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$", message = "비밀번호 형식이 올바르지 않습니다. 8자 이상, 대소문자 포함, 숫자 및 특수문자(@$!%*?&#) 포함")
    private String password;
    @NotNull
    private String userName;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private UserRole userRole;

    public CreateUserDto userDto(String encodedPassword) {
        return new CreateUserDto(loginId, encodedPassword, userName, email, userRole);
    }
}
