package com.example.autoservice.controllers;

import com.example.autoservice.models.User;
import com.example.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (userService.findByUsername(username) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "User with this email already exists"));
        }

        userService.registerUser(user);

        // Создаем Map для успешного ответа
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registration successful");
        response.put("userId", user.getId());
        response.put("username", user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        Map<String, Object> response = new HashMap<>();
        if (existingUser != null && userService.checkPassword(user.getPassword(), existingUser.getPassword())) {
            // Устанавливаем пользователя как аутентифицированного
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(existingUser.getUsername(), null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            response.put("success", true);
            response.put("message", "Вход выполнен успешно.");
            response.put("username", existingUser.getUsername());
            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("message", "Неверный логин или пароль.");
        return ResponseEntity.status(400).body(response);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getHome() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok("Добро пожаловать, " + username + "!");
    }


}
