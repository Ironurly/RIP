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
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> requestBody) {

        System.out.println("Получен запрос: " + requestBody);

        String username = requestBody.get("username");
        String password = requestBody.get("password");

        if (username == null || password == null) {
            System.out.println("Ошибка: Отсутствуют имя пользователя или пароль");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Необходимо указать имя пользователя и пароль.");
            return ResponseEntity.status(400).body(response);
        }

        System.out.println("Регистрация пользователя: " + username);
        if (userService.findByUsername(username) != null) {
            System.out.println("Ошибка: Пользователь уже существует");
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Пользователь уже существует.");
            return ResponseEntity.status(400).body(response);
        }

        User user = new User(username, password);
        userService.registerUser(user);
        System.out.println("Пользователь зарегистрирован: " + username);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Регистрация прошла успешно.");
        return ResponseEntity.ok(response);
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
