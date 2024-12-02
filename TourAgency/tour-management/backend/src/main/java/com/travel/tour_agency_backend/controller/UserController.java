package com.travel.tour_agency_backend.controller;

import com.travel.tour_agency_backend.entity.User;
import com.travel.tour_agency_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.travel.tour_agency_backend.security.JwtTokenProvider;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")

    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> user = userService.getUserByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            String token = tokenProvider.generateToken(email);
            User u = user.get();
            // Создаем Map для ответа
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("username", u.getUsername());
            response.put("isAdmin", "admin".equals(user.get().getUsername()));
            response.put("userId", u.getId()); // Добавляем userId в ответ


            return ResponseEntity.ok(response); // Возвращаем токен в JSON
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}