package com.hospital.controller;

import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.repository.PatientRepository;
import com.hospital.security.CustomUserDetails;
import com.hospital.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientRepository patientRepository;
    private final AppointmentService appointmentService;

    @GetMapping("/patient/dashboard")
    public String dashboard() {
        return "patient/dashboard";
    }

    @GetMapping("/patient/appointments")
    public String myAppointments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        User user = userDetails.getUser();

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ bệnh nhân"));

        model.addAttribute(
                "appointments",
                appointmentService.findByPatient(patient)
        );

        return "patient/appointments";
    }
}