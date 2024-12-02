package com.travel.tour_agency_backend.service;

import com.travel.tour_agency_backend.entity.User;
import com.travel.tour_agency_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Получение всех пользователей
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Получение пользователя по id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Создание нового пользователя
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Обновление данных пользователя
    public User updateUser(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            user.setId(id);  // Обновляем id
            return userRepository.save(user);
        }
        return null;  // Можно выбросить исключение, если пользователь не найден
    }

    // Удаление пользователя
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Используем метод из UserRepository
    }

}