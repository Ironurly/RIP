package com.example.autoservice.controllers;

import com.example.autoservice.models.Request;
import com.example.autoservice.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("requests", requestRepository.findAll());
        return "index"; // Здесь отображается главная страница с выбором входа
    }

    @GetMapping("/home")
    public String homePage() {
        return "home"; // Страница доступна после авторизации
    }

    @PostMapping("/add-request")
    public String addRequest(Request request) {
        requestRepository.save(request);
        return "redirect:/";
    }
}

