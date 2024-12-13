package main.controller;

import lombok.RequiredArgsConstructor;
import main.entity.User;
import main.service.SessionService;
import main.service.UserService;
import main.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        sessionService.setRole(model);
        model.addAttribute("current_page", "register");
        return "register";
    }

    @PostMapping("/register")
    public String register(User user, Model model) throws NoSuchAlgorithmException {
        sessionService.setRole(model);
        model.addAttribute("current_page", "register");
        Result result = userService.register(user);
        if (result.isSuccess()) {
            model.addAttribute("ok", "Пользователь зарегистрирован успешно!");
            return "register";
        } else {
            model.addAttribute("error", result.getError());
            return "register";
        }
    }
}
