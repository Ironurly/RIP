package main.controller;

import lombok.RequiredArgsConstructor;
import main.entity.Manager;
import main.entity.User;
import main.repository.ManagerRepository;
import main.service.ManagerService;
import main.service.SessionService;
import main.utils.MessageConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller()
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;
    private final SessionService sessionService;

    @GetMapping("/managers")
    public String getManagers(Model model) {
        sessionService.setRole(model);
        model.addAttribute("managers", managerService.findAll());
        model.addAttribute("current_page", "managers");
        return "managers";
    }

    @PostMapping("/managers/delete/{id}")
    public String deleteManager(@PathVariable("id") Long id, Model model) {
        if (!managerService.canDelete(id)) {
            model.addAttribute("error", MessageConstants.CANNOT_DELETE_MANAGER);
            return getManagers(model);
        }
        managerService.deleteById(id);
        return "redirect:/managers";
    }

    @PostMapping("/managers/edit/{id}")
    public String editManager(@PathVariable("id") Long id, Model model) {
        sessionService.setRole(model);
        if (!Objects.equals(id, -1L)) {
            model.addAttribute("manager", managerService.findById(id).get());
        } else {
            model.addAttribute("manager", Manager.builder().name("").description("").build());
        }
        model.addAttribute("current_page", "managers");
        return "managers-edit";
    }

    @PostMapping("/managers/save")
    public String saveManager(Manager manager, Model model) {
        managerService.save(manager);
        return "redirect:/managers";
    }
}
