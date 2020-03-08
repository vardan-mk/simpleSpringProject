package com.vardanmk.ChatServiceApp.repos;

import com.vardanmk.ChatServiceApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
