package com.example.autoservice.controllers;

import com.example.autoservice.models.User;
import com.example.autoservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, Object> response = new HashMap<>();

        if (existingUser != null && userService.checkPassword(user.getPassword(), existingUser.getPassword())) {
            response.put("success", true);
            response.put("message", "Вход выполнен успешно.");
            return ResponseEntity.ok(response);
        }

        response.put("success", false);
        response.put("message", "Неверный логин или пароль.");
        return ResponseEntity.status(400).body(response);
    }


}
