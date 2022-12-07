package com.example.Marketplace.controllers;

import com.example.Marketplace.models.Role;
import com.example.Marketplace.models.User;
import com.example.Marketplace.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    private String regView() {
        return "auth/register";
    }

    @PostMapping("/register")
    private String Reg(User user, Model model) {
        User user_from_db = userRepository.findByUsername(user.getUsername());
        if (user_from_db != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "auth/register";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}
