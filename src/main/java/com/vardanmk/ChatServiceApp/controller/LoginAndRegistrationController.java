package com.vardanmk.ChatServiceApp.controller;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class LoginAndRegistrationController {

    private final Logger log = LoggerFactory.getLogger(LoginAndRegistrationController.class);

    @Autowired
    UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/home")
    public String home(@AuthenticationPrincipal User user, Model model) {

        if (user.getRole().name().equals("ADMIN")){
            log.info("Authorized user has role ADMIN and username " + user.getUsername());
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
        log.info("Authorized user has role USER and username " + user.getUsername());
        return "home";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            User user,
//            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) {

        try {
            ControllerUtils.saveFile(user, file, uploadPath);
        } catch (IOException e) {
            user.setFilename("");
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        log.info("New user has successfully added with username " + user.getUsername());
        return "redirect:/login";
    }

    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
