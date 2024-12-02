package com.travel.tour_agency_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users") // Указываем имя таблицы в базе данных
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd") // Отображение только даты без времени
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        // Устанавливаем текущую дату и время перед сохранением объекта
        this.createdAt = LocalDateTime.now();
    }

    public String getUsername() {  // !!! Добавляем геттер для username
        return this.username;
    }
}