package com.hyeonlo.board.domain.user.repository;

import com.hyeonlo.board.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByLoginId(String loginId);

    Users findByLoginId(String loginId);
}
