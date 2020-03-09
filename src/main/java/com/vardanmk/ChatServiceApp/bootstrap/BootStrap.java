package com.vardanmk.ChatServiceApp.bootstrap;


import com.vardanmk.ChatServiceApp.enums.Role;
import com.vardanmk.ChatServiceApp.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import com.vardanmk.ChatServiceApp.service.UserService;
import com.vardanmk.ChatServiceApp.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BootStrap implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(BootStrap.class);

    private final UserService userService;
    private final Environment env;

    public BootStrap(
                     UserService userService,
                     Environment env) {
        this.userService = userService;
        this.env = env;
    }

    @Override
    @Transactional
    public void run(String... args) {

// ==============  Creat Admin User  ====================
       User adminUser = userService.createUser(
                env.getProperty("user.admin.username"),
                env.getProperty("user.admin.firstName"),
                env.getProperty("user.admin.lastName"),
                env.getProperty("user.admin.password"),
                Role.ADMIN);

       log.info("ADMIN user created successfully");
    }
}







