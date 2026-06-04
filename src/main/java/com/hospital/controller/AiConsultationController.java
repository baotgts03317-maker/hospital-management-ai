package com.hospital.controller;

import com.hospital.entity.AiConsultation;
import com.hospital.service.AiConsultationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient/ai-consultation")
public class AiConsultationController {

    private final AiConsultationService service;

    public AiConsultationController(AiConsultationService service) {
        this.service = service;
    }

    @GetMapping
    public String showPage(Model model) {
        Long patientId = 1L; // tạm thời test
        model.addAttribute("history", service.getHistory(patientId));
        return "patient/ai-consultation";
    }

    @PostMapping
    public String analyze(@RequestParam("symptoms") String symptoms, Model model) {
        Long patientId = 1L; // tạm thời test

        AiConsultation result = service.analyzeSymptoms(patientId, symptoms);

        model.addAttribute("result", result);
        model.addAttribute("recommendedDoctors", service.findRecommendedDoctors(result.getSuggestedSpecialization()));
        model.addAttribute("history", service.getHistory(patientId));

        return "patient/ai-consultation";
    }
}