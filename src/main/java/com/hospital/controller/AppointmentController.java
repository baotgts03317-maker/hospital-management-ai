package com.hospital.controller;

import com.hospital.dto.AppointmentCreateDTO;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.entity.User;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.security.CustomUserDetails;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @GetMapping("/book/{doctorId}")
    public String bookForm(
            @PathVariable Long doctorId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        User user = userDetails.getUser();

        Patient patient = patientRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ bệnh nhân"));

        AppointmentCreateDTO dto = new AppointmentCreateDTO();
        dto.setDoctorId(doctorId);
        dto.setPatientId(patient.getId());

        model.addAttribute("appointmentDTO", dto);
        model.addAttribute("doctor", doctorService.findById(doctorId));

        return "appointment/book";
    }

    @PostMapping("/book")
    public String book(
            @Valid @ModelAttribute("appointmentDTO") AppointmentCreateDTO dto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("doctor", doctorService.findById(dto.getDoctorId()));
            return "appointment/book";
        }

        appointmentService.bookAppointment(dto);

        return "redirect:/patient/appointments?success=true";
    }

    @GetMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "redirect:/patient/appointments";
    }

    @GetMapping("/doctor/confirm/{id}")
    public String confirm(@PathVariable Long id) {
        appointmentService.confirmAppointment(id);
        return "redirect:/doctor/appointments";
    }

    @GetMapping("/doctor/complete/{id}")
    public String complete(@PathVariable Long id) {
        appointmentService.completeAppointment(id);
        return "redirect:/doctor/appointments";
    }
}