package main.controller;

import lombok.RequiredArgsConstructor;
import main.entity.User;
import main.service.SessionService;
import main.service.UserService;
import main.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final SessionService sessionService;
    private final UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        sessionService.setRole(model);
        model.addAttribute("current_page", "login");
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, Model model) throws NoSuchAlgorithmException {
        sessionService.setRole(model);
        Result result = sessionService.login(user.getLogin(), user.getPassword());
        if (result.isSuccess()) {
            return "redirect:/";
        } else {
            model.addAttribute("error", result.getError());
            model.addAttribute("current_page", "login");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        User user = sessionService.getCurrentUser();
        sessionService.logout(user.getLogin());
        return "redirect:/";
    }
}
