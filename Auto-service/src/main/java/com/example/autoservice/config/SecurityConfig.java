package com.example.autoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/", "/register", "/login").permitAll()  // Разрешаем доступ к страницам регистрации и входа
                        .anyRequest().authenticated())  // Все остальные страницы требуют аутентификации
                .formLogin((form) -> form
                        .loginPage("/login")  // Страница входа
                        .permitAll())
                .logout((logout) -> logout
                        .permitAll());

        return http.build();
    }
}
