package com.vardanmk.ChatServiceApp.controller;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class LoginAndRegistrationController {

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
            return "redirect:/admin";
        }
        model.addAttribute("user", user);
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
        return "redirect:/login";
    }

    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}
