package com.hyeonlo.board.domain.infra.userclient;

import com.hyeonlo.board.domain.infra.userclient.dto.CreateUserDto;
import com.hyeonlo.board.domain.infra.userclient.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserClient {
    boolean existsByLoginId(String loginId);

    void createUser(CreateUserDto createUserDto);

    UserDto findByLoginId(String loginId);
}
