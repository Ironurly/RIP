package main.entity;

import jakarta.persistence.*;
import lombok.*;
import main.utils.Role;

/**
 * Сущность «Пользователь».
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Логин пользователя.
     */
    @Column(name = "login")
    private String login;

    /**
     * ФИО пользователя.
     */
    @Column(name = "name")
    private String name;

    /**
     * Хеш пароля пользователя.
     */
    @Column(name = "password")
    private String password;

    /**
     * Номер телефона.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Роль пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
