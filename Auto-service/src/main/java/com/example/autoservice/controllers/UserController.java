package com.example.autoservice.controllers;

import com.example.autoservice.models.User;
import com.example.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Пользователь уже существует.\"}");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("{\"success\": true, \"message\": \"Регистрация прошла успешно.\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && userService.checkPassword(user, existingUser)) {
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Вход успешен.\"}");
        }
        return ResponseEntity.status(400).body("{\"success\": false, \"message\": \"Неверный логин или пароль.\"}");
    }
}
