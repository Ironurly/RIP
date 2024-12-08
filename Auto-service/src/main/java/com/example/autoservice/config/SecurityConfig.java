package com.example.autoservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключаем CSRF для консоли H2
                .headers(headers -> headers.frameOptions().disable()) // Разрешаем использование фреймов для H2 Console
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/api/**", "/css/**", "/js/**", "/h2-console/**", "/home").permitAll() // Разрешаем доступ к консоли H2
                        .requestMatchers("/services/**").authenticated() // Эти URL доступны только аутентифицированным пользователям
                        .anyRequest().authenticated()) // Остальные URL требуют авторизации
                .formLogin(form -> form.disable()) // Отключаем стандартную форму логина Spring Security
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода
                        .logoutSuccessUrl("/") // После выхода перенаправляем на главную
                        .invalidateHttpSession(true) // Очистка сессии
                        .clearAuthentication(true) // Очистка аутентификации
                        .permitAll());

        return http.build();
    }
}
