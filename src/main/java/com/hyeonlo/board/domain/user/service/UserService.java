package com.hyeonlo.board.domain.user.service;

import com.hyeonlo.board.domain.infra.userclient.dto.CreateUserDto;
import com.hyeonlo.board.domain.infra.userclient.dto.UserDto;
import com.hyeonlo.board.domain.user.dto.request.UpdateUserRequest;
import com.hyeonlo.board.domain.user.dto.response.UpdateUserResponse;
import com.hyeonlo.board.domain.user.dto.response.UserResponse;
import com.hyeonlo.board.domain.user.entity.Users;
import com.hyeonlo.board.domain.user.enums.UserRole;
import com.hyeonlo.board.domain.user.enums.UserStatus;
import com.hyeonlo.board.domain.user.repository.UserRepository;
import com.hyeonlo.board.global.apipayload.status.ErrorStatus;
import com.hyeonlo.board.global.error.BaseException;
import com.hyeonlo.board.global.security.AuthUser;
import com.hyeonlo.board.global.utils.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Transactional
    public void createUser(CreateUserDto createUser) {
        Users user = Users.of(
                createUser.getLoginId(),
                createUser.getPassword(),
                createUser.getUserName(),
                createUser.getEmail(),
                createUser.getUserRole()
        );

        userRepository.save(user);
    }
    //본인 프로필 조회
    public UserResponse getUser(AuthUser authUser) {
        Users user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BaseException(ErrorStatus.USER_NOT_FOUND));

        return UserResponse.of(
                user.getUserId(),
                user.getLoginId(),
                user.getUserName(),
                user.getEmail()
        );
    }

    @Transactional
    public UpdateUserResponse updateUser(AuthUser authUser, UpdateUserRequest updateUserRequest) {
        Users user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BaseException(ErrorStatus.USER_NOT_FOUND));

        if(!passwordEncoder.matches(updateUserRequest.getCurrentPassword(), user.getPassword())) {
            throw new BaseException(ErrorStatus.INVALID_PASSWORD);
        }

        if(updateUserRequest.getLoginId() != null && !updateUserRequest.getLoginId().isBlank()) {
            user.updateLoginId(updateUserRequest.getLoginId());
        }

        if(updateUserRequest.getNewPassword() != null && !updateUserRequest.getNewPassword().isBlank()) {
            String newEncodedPassword = passwordEncoder.encode(updateUserRequest.getNewPassword());
            user.updatePassword(newEncodedPassword);
        }

        if(updateUserRequest.getUserName() != null && !updateUserRequest.getUserName().isBlank()) {
            user.updateUserName(updateUserRequest.getUserName());
        }

        if(updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isBlank()) {
            user.updateEmail(updateUserRequest.getEmail());
        }

        userRepository.save(user);

        return UpdateUserResponse.of(
                "회원 정보 수정 완료"
        );
    }

    public void deleteUser(AuthUser authUser) {
        Users user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BaseException(ErrorStatus.USER_NOT_FOUND));

        if(user.getUserStatus().equals(UserStatus.WITHDRAW)) {
            throw new BaseException(ErrorStatus.ALREADY_DELETE);
        }
        user.changeStatus();
    }

    public boolean existsByLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public UserDto findByLoginId(String loginId) {
        Users user = userRepository.findByLoginId(loginId);
        return new UserDto(user.getUserId(),user.getLoginId(),user.getPassword(), user.getUserRole(), user.getUserStatus());
    }


}
