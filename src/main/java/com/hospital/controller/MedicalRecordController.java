package com.hospital.controller;

import com.hospital.dto.MedicalRecordCreateDTO;
import com.hospital.dto.MedicalRecordSearchDTO;
import com.hospital.dto.MedicalRecordUpdateDTO;
import com.hospital.entity.MedicalRecord;
import com.hospital.repository.AppointmentRepository;
import com.hospital.service.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/doctor/medical-records/create/{appointmentId}")
    public String createForm(
            @PathVariable Long appointmentId,
            Model model) {

        MedicalRecordCreateDTO dto = new MedicalRecordCreateDTO();
        dto.setAppointmentId(appointmentId);

        model.addAttribute("recordDTO", dto);
        model.addAttribute("appointment",
                appointmentRepository.findById(appointmentId).orElseThrow());

        return "doctor/create-record";
    }

    @PostMapping("/doctor/medical-records/create")
    public String create(
            @Valid @ModelAttribute("recordDTO") MedicalRecordCreateDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "doctor/create-record";
        }

        medicalRecordService.create(dto);

        return "redirect:/doctor/medical-records";
    }

    @GetMapping("/doctor/medical-records")
    public String doctorRecords(
            @ModelAttribute MedicalRecordSearchDTO searchDTO,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(
                page,
                10,
                Sort.by("createdAt").descending()
        );

        Page<MedicalRecord> recordPage =
                medicalRecordService.search(searchDTO, pageable);

        model.addAttribute("recordPage", recordPage);
        model.addAttribute("searchDTO", searchDTO);

        return "doctor/medical-records";
    }

    @GetMapping("/doctor/medical-records/edit/{id}")
    public String editForm(
            @PathVariable Long id,
            Model model) {

        MedicalRecord record = medicalRecordService.findById(id);

        MedicalRecordUpdateDTO dto = new MedicalRecordUpdateDTO();
        dto.setMedicalRecordId(record.getId());
        dto.setSymptoms(record.getSymptoms());
        dto.setDiagnosis(record.getDiagnosis());
        dto.setPrescription(record.getPrescription());
        dto.setDoctorNotes(record.getDoctorNotes());

        model.addAttribute("record", record);
        model.addAttribute("recordDTO", dto);

        return "doctor/edit-record";
    }

    @PostMapping("/doctor/medical-records/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute("recordDTO") MedicalRecordUpdateDTO dto) {

        medicalRecordService.update(id, dto);

        return "redirect:/doctor/medical-records";
    }

    @GetMapping("/patient/medical-records")
    public String patientRecords(
            @ModelAttribute MedicalRecordSearchDTO searchDTO,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(
                page,
                10,
                Sort.by("createdAt").descending()
        );

        Page<MedicalRecord> recordPage =
                medicalRecordService.search(searchDTO, pageable);

        model.addAttribute("recordPage", recordPage);
        model.addAttribute("searchDTO", searchDTO);

        return "patient/medical-records";
    }

    @GetMapping("/admin/medical-records")
    public String adminRecords(
            @ModelAttribute MedicalRecordSearchDTO searchDTO,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(
                page,
                10,
                Sort.by("createdAt").descending()
        );

        Page<MedicalRecord> recordPage =
                medicalRecordService.search(searchDTO, pageable);

        model.addAttribute("recordPage", recordPage);
        model.addAttribute("searchDTO", searchDTO);

        return "admin/medical-records";
    }
}