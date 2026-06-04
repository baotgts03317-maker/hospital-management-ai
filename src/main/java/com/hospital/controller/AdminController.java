package com.hospital.controller;

import com.hospital.entity.User;
import com.hospital.service.AdminDashboardService;
import com.hospital.service.DashboardService;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final DashboardService dashboardService;

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute(
                "totalUsers",
                dashboardService.totalUsers()
        );

        model.addAttribute(
                "totalDoctors",
                dashboardService.totalDoctors()
        );

        model.addAttribute(
                "totalPatients",
                dashboardService.totalPatients()
        );

        model.addAttribute(
                "totalAppointments",
                dashboardService.totalAppointments()
        );

        model.addAttribute(
                "totalMedicalRecords",
                dashboardService.totalMedicalRecords()
        );

        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(Model model) {

        List<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "admin/users";
    }

    @GetMapping("/users/lock/{id}")
    public String lockUser(
            @PathVariable Long id) {

        userService.lockUser(id);

        return "redirect:/admin/users";
    }

    @GetMapping("/users/unlock/{id}")
    public String unlockUser(
            @PathVariable Long id) {

        userService.unlockUser(id);

        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(
            @PathVariable Long id) {

        userService.delete(id);

        return "redirect:/admin/users";
    }
    private final AdminDashboardService adminDashboardService;
}