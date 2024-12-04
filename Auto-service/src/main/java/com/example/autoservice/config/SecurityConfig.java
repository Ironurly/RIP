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
                        .requestMatchers("/", "/register", "/login", "/css/**", "/js/**").permitAll() // Доступ к публичным ресурсам
                        .anyRequest().authenticated()) // Остальные URL требуют авторизации
                .formLogin((form) -> form
                        .loginPage("/") // Главная страница выполняет роль выбора входа
                        .loginProcessingUrl("/login") // URL для обработки логина
                        .defaultSuccessUrl("/home", true) // После успешного входа перенаправляем на /home
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .logoutSuccessUrl("/") // После выхода перенаправляем на главную
                        .invalidateHttpSession(true) // Очистка сессии
                        .clearAuthentication(true) // Очистка аутентификации
                        .permitAll());


        return http.build();
    }
}
