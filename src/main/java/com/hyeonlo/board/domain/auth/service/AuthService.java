package com.hyeonlo.board.domain.auth.service;

import com.hyeonlo.board.domain.auth.dto.request.CreateUserRequest;
import com.hyeonlo.board.domain.auth.dto.request.LoginRequest;
import com.hyeonlo.board.domain.auth.dto.response.CreateUserResponse;
import com.hyeonlo.board.domain.auth.dto.response.LoginResponse;
import com.hyeonlo.board.domain.infra.userclient.UserClient;
import com.hyeonlo.board.domain.infra.userclient.dto.UserDto;
import com.hyeonlo.board.domain.user.enums.UserStatus;
import com.hyeonlo.board.global.apipayload.status.ErrorStatus;
import com.hyeonlo.board.global.error.BaseException;
import com.hyeonlo.board.global.security.JwtUtil;
import com.hyeonlo.board.global.utils.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserClient userClient;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if(userClient.existsByLoginId(createUserRequest.getLoginId())) {
            throw new BaseException(ErrorStatus.ALREADY_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        userClient.createUser(createUserRequest.userDto(encodedPassword));

        return CreateUserResponse.of(
                "사용자 회원가입 성공");
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UserDto user = userClient.findByLoginId(loginRequest.loginId);

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorStatus.INVALID_PASSWORD);
        }

        if(user.getUserStatus().equals(UserStatus.WITHDRAW)) {
        throw new BaseException(ErrorStatus.ALREADY_DELETE);
        }

        String token = jwtUtil.createToken(user.getUserId(),user.getLoginId(),user.getUserRole());
        return LoginResponse.of(
                "로그인 성공",
                token
        );
    }
}
