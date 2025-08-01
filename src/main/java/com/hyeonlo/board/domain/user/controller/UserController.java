package com.hyeonlo.board.domain.user.controller;

import com.hyeonlo.board.domain.user.dto.request.UpdateUserRequest;
import com.hyeonlo.board.domain.user.dto.response.UpdateUserResponse;
import com.hyeonlo.board.domain.user.dto.response.UserResponse;
import com.hyeonlo.board.domain.user.service.UserService;
import com.hyeonlo.board.global.apipayload.BaseResponse;
import com.hyeonlo.board.global.apipayload.status.SuccessStatus;
import com.hyeonlo.board.global.security.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/v1")
    public BaseResponse<UserResponse> getUser(@AuthenticationPrincipal AuthUser authUser) {
        UserResponse response = userService.getUser(authUser);
        return BaseResponse.success(SuccessStatus.ACCEPTED, response);
    }

    @PatchMapping("/v1")
    public BaseResponse<UpdateUserResponse> updateUser(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody UpdateUserRequest updateUserRequest) {
        UpdateUserResponse response = userService.updateUser(authUser, updateUserRequest);
        return BaseResponse.success(SuccessStatus.UPDATE_SUCCESS, response);
    }

    @DeleteMapping("/v1")
    public BaseResponse<Void> deleteUser(@AuthenticationPrincipal AuthUser authUser) {
        userService.deleteUser(authUser);
        return BaseResponse.success(SuccessStatus.DELETE_USER, null);
    }

}
