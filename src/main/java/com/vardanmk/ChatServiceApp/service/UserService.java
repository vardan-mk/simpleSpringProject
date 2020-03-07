package com.vardanmk.ChatServiceApp.service;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    boolean addUser(User user);
    User createUser(String username, String firstname, String lastname, String password);

//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
