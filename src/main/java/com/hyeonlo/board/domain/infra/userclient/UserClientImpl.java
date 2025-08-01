package com.hyeonlo.board.domain.infra.userclient;

import com.hyeonlo.board.domain.infra.userclient.dto.CreateUserDto;
import com.hyeonlo.board.domain.infra.userclient.dto.UserDto;
import com.hyeonlo.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserClientImpl implements UserClient{

    private final UserService userService;

    @Override
    public boolean existsByLoginId(String loginId) {
        return userService.existsByLoginId(loginId);
    }

    @Override
    public void createUser(CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
    }

    @Override
    public UserDto findByLoginId(String loginId) {
        return userService.findByLoginId(loginId);
    }
}
