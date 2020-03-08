package com.vardanmk.ChatServiceApp.controller;

import com.vardanmk.ChatServiceApp.domain.User;
import com.vardanmk.ChatServiceApp.enums.Role;
import com.vardanmk.ChatServiceApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserController {

    @Autowired
    UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("path", uploadPath);

        return "admin";
    }

    @PostMapping("/adduser")
    public String addNewUser(
            User user,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        try {
            ControllerUtils.saveFile(user, file, uploadPath);
        } catch (IOException e) {
            user.setFilename("");
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "admin?error=notCreate";
        }
        return "redirect:/admin";
    }

    @GetMapping("/edituser/{id}")
    public String userEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);

        return "edituser";
    }

    @PostMapping("/edituser/{id}")
    public String updateUser(
            @PathVariable("id") Long id,
            @Valid User user,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "edituser";
        }
        User user1 = userService.getUser(id);
        try {
            ControllerUtils.saveFile(user, file, uploadPath);
        } catch (Exception e) {
            user.setFilename("");
        }
        if(!userService.updateUser(user)) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "redirect:/admin";
    }

    @GetMapping("/userdelete/{id}")
    public String userRemove(@NonNull @PathVariable Long id) {
        userService.deleteUser(id);
//        log.info("Manager deleted User by Id, Manager -> {}, deleted user id -> {}", username, id);
        return "redirect:/admin";
    }
}
