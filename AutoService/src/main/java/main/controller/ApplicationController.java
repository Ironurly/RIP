package main.controller;

import lombok.RequiredArgsConstructor;
import main.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
    private final SessionService sessionService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        sessionService.setRole(model);
        model.addAttribute("current_page", "index");
        return "index";
    }
}
