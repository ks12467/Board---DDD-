package com.hyeonlo.board.domain.auth.controller;

import com.hyeonlo.board.domain.auth.dto.request.CreateUserRequest;
import com.hyeonlo.board.domain.auth.dto.request.LoginRequest;
import com.hyeonlo.board.domain.auth.dto.response.CreateUserResponse;
import com.hyeonlo.board.domain.auth.dto.response.LoginResponse;
import com.hyeonlo.board.domain.auth.service.AuthService;
import com.hyeonlo.board.global.apipayload.BaseResponse;
import com.hyeonlo.board.global.apipayload.status.SuccessStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/v1")
    public BaseResponse<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse response = authService.createUser(createUserRequest);
        return BaseResponse.success(SuccessStatus.CREATE_USER, response);
    }

    @PostMapping("/v1/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return BaseResponse.success(SuccessStatus.LOGIN_SUCCESS, response);
    }
}
