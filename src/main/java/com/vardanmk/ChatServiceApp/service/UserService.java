package com.vardanmk.ChatServiceApp.service;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    boolean addUser(User user);
    User createUser(String username, String firstname, String lastname, String password, Role role);

    User getUser(Long id);
    List<User> getAllUsers();
//    User updateUser(User user);

    void deleteUser(Long id);

}
