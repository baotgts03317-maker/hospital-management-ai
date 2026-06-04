package com.hospital.service;

import com.hospital.entity.AiMedicalSummary;
import com.hospital.entity.MedicalRecord;
import com.hospital.repository.AiMedicalSummaryRepository;
import com.hospital.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class AiMedicalSummaryService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AiMedicalSummaryRepository aiMedicalSummaryRepository;

    public AiMedicalSummaryService(
            MedicalRecordRepository medicalRecordRepository,
            AiMedicalSummaryRepository aiMedicalSummaryRepository
    ) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.aiMedicalSummaryRepository = aiMedicalSummaryRepository;
    }

    public AiMedicalSummary generateSummary(Long medicalRecordId) {
        MedicalRecord record = medicalRecordRepository.findById(medicalRecordId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh án"));

        String symptoms = safe(record.getSymptoms());
        String diagnosis = safe(record.getDiagnosis());
        String prescription = safe(record.getPrescription());
        String doctorNotes = safe(record.getDoctorNotes());

        String originalContent =
                "Triệu chứng: " + symptoms + "\n" +
                "Chẩn đoán: " + diagnosis + "\n" +
                "Đơn thuốc: " + prescription + "\n" +
                "Ghi chú bác sĩ: " + doctorNotes;

        AiMedicalSummary summary = aiMedicalSummaryRepository
                .findByMedicalRecordId(medicalRecordId)
                .orElse(new AiMedicalSummary());

        summary.setMedicalRecordId(medicalRecordId);
        summary.setOriginalContent(originalContent);

        summary.setKeySymptoms(generateKeySymptoms(symptoms));
        summary.setDiagnosisSummary(generateDiagnosisSummary(diagnosis));
        summary.setTreatmentSummary(generateTreatmentSummary(prescription, doctorNotes));
        summary.setFollowUpNote(generateFollowUpNote(symptoms, diagnosis, doctorNotes));

        summary.setSummary(
                "Bệnh nhân có triệu chứng chính: " + summary.getKeySymptoms()
                        + ". Chẩn đoán sơ bộ: " + summary.getDiagnosisSummary()
                        + ". Hướng điều trị: " + summary.getTreatmentSummary()
                        + ". Ghi chú theo dõi: " + summary.getFollowUpNote()
        );

        return aiMedicalSummaryRepository.save(summary);
    }

    public AiMedicalSummary getSummary(Long medicalRecordId) {
        return aiMedicalSummaryRepository.findByMedicalRecordId(medicalRecordId)
                .orElse(null);
    }

    private String generateKeySymptoms(String symptoms) {
        return symptoms.isBlank() ? "Chưa có thông tin triệu chứng." : symptoms;
    }

    private String generateDiagnosisSummary(String diagnosis) {
        return diagnosis.isBlank() ? "Chưa có chẩn đoán cụ thể." : diagnosis;
    }

    private String generateTreatmentSummary(String prescription, String doctorNotes) {
        StringBuilder result = new StringBuilder();

        if (!prescription.isBlank()) {
            result.append("Đơn thuốc: ").append(prescription).append(". ");
        }

        if (!doctorNotes.isBlank()) {
            result.append("Ghi chú bác sĩ: ").append(doctorNotes).append(".");
        }

        if (result.isEmpty()) {
            return "Chưa có thông tin điều trị.";
        }

        return result.toString();
    }

    private String generateFollowUpNote(String symptoms, String diagnosis, String doctorNotes) {
        String lower = (symptoms + " " + diagnosis + " " + doctorNotes).toLowerCase();

        if (lower.contains("sốt") || lower.contains("ho") || lower.contains("viêm")) {
            return "Nên tái khám nếu triệu chứng kéo dài sau 3 ngày hoặc có dấu hiệu nặng hơn.";
        }

        if (lower.contains("tim") || lower.contains("đau ngực") || lower.contains("khó thở")) {
            return "Cần theo dõi sát triệu chứng tim mạch và tái khám đúng lịch hẹn.";
        }

        if (lower.contains("dị ứng") || lower.contains("ngứa") || lower.contains("nổi mẩn")) {
            return "Theo dõi phản ứng da và tránh các tác nhân gây dị ứng.";
        }

        return "Theo dõi tình trạng sức khỏe và tái khám theo hướng dẫn của bác sĩ.";
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}