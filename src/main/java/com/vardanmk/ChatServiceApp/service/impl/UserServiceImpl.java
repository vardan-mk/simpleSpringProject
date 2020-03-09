package com.vardanmk.ChatServiceApp.service.impl;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.enums.Role;
import com.vardanmk.ChatServiceApp.repos.UserRepo;
import com.vardanmk.ChatServiceApp.service.UserService;
import com.vardanmk.ChatServiceApp.util.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public User createUser(String username, String firstname, String lastname, String password, Role role) {
        User user = new User();
        user.setUsername(username);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        user.setRole(role);
        log.info(" this method used only in BootStrap class to create an admin with username  -> {}", username);
        return userRepo.save(user);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        userRepo.save(user);
        log.info("user created with username  -> {}", user.getUsername());
        return true;
    }

    public boolean updateUser(User user) {
        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setLastname(user.getUsername());
        user.setLastname(user.getPassword());
        user.setFilename(user.getFilename());
        user.setRole(Role.USER);
        userRepo.save(user);
        log.info("user was updated");
        return true;
    }

    @Override
    public User getUser(Long id) {
        log.info("Get user by id -> {}", id);
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not found by Id"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Try to remove user by id -> {}", id);
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not found User by Id for remove"));
        userRepo.delete(user);
    }

}
