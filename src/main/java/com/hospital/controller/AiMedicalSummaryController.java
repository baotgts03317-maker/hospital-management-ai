package com.hospital.controller;

import com.hospital.entity.AiMedicalSummary;
import com.hospital.service.AiMedicalSummaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctor/medical-records")
public class AiMedicalSummaryController {

    private final AiMedicalSummaryService aiMedicalSummaryService;

    public AiMedicalSummaryController(AiMedicalSummaryService aiMedicalSummaryService) {
        this.aiMedicalSummaryService = aiMedicalSummaryService;
    }

    @PostMapping("/{id}/ai-summary")
    public String generateSummary(@PathVariable("id") Long id) {
        aiMedicalSummaryService.generateSummary(id);
        return "redirect:/doctor/medical-records/" + id + "/ai-summary";
    }

    @GetMapping("/{id}/ai-summary")
    public String viewSummary(@PathVariable("id") Long id, Model model) {
        AiMedicalSummary summary = aiMedicalSummaryService.getSummary(id);

        model.addAttribute("summary", summary);
        model.addAttribute("medicalRecordId", id);

        return "doctor/ai-medical-summary";
    }
}