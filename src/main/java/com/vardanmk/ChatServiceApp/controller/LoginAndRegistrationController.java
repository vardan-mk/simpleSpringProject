package com.vardanmk.ChatServiceApp.controller;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginAndRegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String userEditForm() {

        return "admin";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(

            User user,
//            BindingResult bindingResult,
            Model model
    ) {


//        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
//
//        if (isConfirmEmpty) {
//            model.addAttribute("password2Error", "Password confirmation cannot be empty");
//        }
//
//        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
//            model.addAttribute("passwordError", "Passwords are different!");
//        }
//
//        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
//            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
//
//            model.mergeAttributes(errors);
//
//            return "registration";
//        }
//
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/home";
    }

}
