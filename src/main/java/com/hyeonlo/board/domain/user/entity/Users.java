package com.hyeonlo.board.domain.user.entity;

import com.hyeonlo.board.domain.user.enums.UserRole;
import com.hyeonlo.board.domain.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String loginId;

    private String password;

    private String userName;

    private String email;

    @Enumerated
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Enumerated
    private UserRole userRole;

    private Users(String loginId, String password, String userName, String email, UserRole userRole) {
        this.loginId = loginId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.userRole = userRole;
    }

    public static Users of(String loginId, String password, String userName, String email, UserRole userRole) {
        return new Users(
                loginId,
                password,
                userName,
                email,
                userRole
        );
    }

    public void updateLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void changeStatus() {
        this.userStatus = UserStatus.WITHDRAW;
    }
}
