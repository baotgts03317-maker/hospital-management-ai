package com.hospital.controller;

import com.hospital.entity.Doctor;
import com.hospital.entity.User;
import com.hospital.repository.DoctorRepository;
import com.hospital.security.CustomUserDetails;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @GetMapping("/doctor/dashboard")
    public String dashboard() {
        return "doctor/dashboard";
    }

    @GetMapping("/doctor/appointments")
    public String doctorAppointments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        User user = userDetails.getUser();

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ bác sĩ"));

        model.addAttribute(
                "appointments",
                appointmentService.findByDoctor(doctor)
        );
        
        model.addAttribute("doctorUserId", user.getId());
        return "doctor/appointments";
    }

    @GetMapping("/doctors")
    public String doctorList(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        return "doctor/list";
    }
}