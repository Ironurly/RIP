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
                        .requestMatchers("/", "/register", "/css/**", "/js/**").permitAll() // Разрешаем доступ к публичным ресурсам
                        .anyRequest().authenticated()) // Всё остальное требует авторизации
                .formLogin((form) -> form
                        .loginPage("/") // На главной странице будет предложен выбор способа входа
                        .defaultSuccessUrl("/home", true) // После авторизации — на /home
                        .permitAll())
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login") // После выхода перенаправляем на страницу входа
                        .permitAll());

        return http.build();
    }
}
