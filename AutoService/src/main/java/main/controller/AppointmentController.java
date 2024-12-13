package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.AppointmentDto;
import main.dto.CriteriaDto;
import main.service.AppointmentService;
import main.service.ManagerService;
import main.service.ProductService;
import main.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AppointmentController {
    private final ProductService productService;
    private final AppointmentService appointmentService;
    private final SessionService sessionService;
    private final ManagerService managerService;

    @PostMapping("/appointments/by-criteria")
    public String getAppointmentsByManager(CriteriaDto criteria, Model model) {
        sessionService.setRole(model);
        model.addAttribute("managers", managerService.findAll());
        model.addAttribute("appointments", appointmentService.findByCriteria(criteria));
        model.addAttribute("current_page", "appointments");
        return "appointments";
    }

    @GetMapping("/appointments")
    public String getAppointments(Model model) {
        sessionService.setRole(model);
        model.addAttribute("managers", managerService.findAll());
        if (Objects.equals(model.getAttribute("role"), "USER")) {
            model.addAttribute("appointments", appointmentService.findCurrentUserAppointments());
        } else {
            model.addAttribute("appointments", appointmentService.findAll().stream().map(appointmentService::mapToAppointmentDto).collect(Collectors.toList()));
        }
        model.addAttribute("current_page", "appointments");
        return "appointments";
    }

    @PostMapping("appointments/edit/{id}/{productId}")
    public String editAppointment(@PathVariable("id") Long id, @PathVariable("productId") Long productId, Model model) throws ParseException {
        sessionService.setRole(model);
        model.addAttribute("appointment", appointmentService.createDto(id, productId));
        model.addAttribute("current_page", "appointments");
        return "appointments-edit";
    }

    @PostMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable("id") Long id, Model model) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }

    @PostMapping("/appointments/save")
    public String saveManager(AppointmentDto appointmentDto, Model model) throws ParseException {
        appointmentService.save(appointmentService.mapToAppointment(appointmentDto));
        return "redirect:/appointments";
    }
}
