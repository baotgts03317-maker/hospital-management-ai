package com.hospital.service;

import com.hospital.entity.AiConsultation;
import com.hospital.entity.AiDoctorRecommendation;
import com.hospital.entity.Doctor;
import com.hospital.repository.AiConsultationRepository;
import com.hospital.repository.AiDoctorRecommendationRepository;
import com.hospital.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiConsultationService {

    private final AiConsultationRepository consultationRepository;
    private final DoctorRepository doctorRepository;
    private final AiDoctorRecommendationRepository recommendationRepository;

    public AiConsultationService(
            AiConsultationRepository consultationRepository,
            DoctorRepository doctorRepository,
            AiDoctorRecommendationRepository recommendationRepository
    ) {
        this.consultationRepository = consultationRepository;
        this.doctorRepository = doctorRepository;
        this.recommendationRepository = recommendationRepository;
    }

    public AiConsultation analyzeSymptoms(Long patientId, String symptoms) {
        String lower = symptoms.toLowerCase();

        AiConsultation consultation = new AiConsultation();
        consultation.setPatientId(patientId);
        consultation.setSymptomsInput(symptoms);

        if (lower.contains("đau ngực") || lower.contains("khó thở")) {
            consultation.setRiskLevel("HIGH");
            consultation.setSuggestedSpecialization("Tim mạch");
            consultation.setEmergencyWarning(true);
            consultation.setAiAnalysis("Triệu chứng có dấu hiệu nguy hiểm, cần được kiểm tra sớm.");
            consultation.setAdvice("Bạn nên đến cơ sở y tế gần nhất hoặc đặt lịch khám chuyên khoa Tim mạch càng sớm càng tốt.");
        } else if (lower.contains("sốt") || lower.contains("ho") || lower.contains("đau họng")) {
            consultation.setRiskLevel("MEDIUM");
            consultation.setSuggestedSpecialization("Nội tổng quát");
            consultation.setEmergencyWarning(false);
            consultation.setAiAnalysis("Triệu chứng có thể liên quan đến cảm cúm, viêm họng hoặc nhiễm khuẩn đường hô hấp.");
            consultation.setAdvice("Nên uống nhiều nước, nghỉ ngơi, theo dõi nhiệt độ và đặt lịch khám nếu triệu chứng kéo dài trên 2-3 ngày.");
        } else if (lower.contains("đau bụng") || lower.contains("buồn nôn")) {
            consultation.setRiskLevel("MEDIUM");
            consultation.setSuggestedSpecialization("Tiêu hóa");
            consultation.setEmergencyWarning(false);
            consultation.setAiAnalysis("Triệu chứng có thể liên quan đến rối loạn tiêu hóa hoặc bệnh lý đường ruột.");
            consultation.setAdvice("Nên ăn nhẹ, uống đủ nước và đặt lịch khám chuyên khoa Tiêu hóa nếu đau kéo dài.");
        } else if (lower.contains("nổi mẩn") || lower.contains("ngứa") || lower.contains("dị ứng")) {
            consultation.setRiskLevel("LOW");
            consultation.setSuggestedSpecialization("Da liễu");
            consultation.setEmergencyWarning(false);
            consultation.setAiAnalysis("Triệu chứng có thể liên quan đến dị ứng hoặc vấn đề da liễu.");
            consultation.setAdvice("Tránh gãi mạnh, không tự ý dùng thuốc và nên khám Da liễu nếu vùng da lan rộng.");
        } else {
            consultation.setRiskLevel("LOW");
            consultation.setSuggestedSpecialization("Nội tổng quát");
            consultation.setEmergencyWarning(false);
            consultation.setAiAnalysis("AI chưa xác định được nhóm triệu chứng rõ ràng.");
            consultation.setAdvice("Bạn nên đặt lịch khám Nội tổng quát để được bác sĩ tư vấn chính xác hơn.");
        }

        AiConsultation saved = consultationRepository.save(consultation);

        generateDoctorRecommendations(saved);

        return saved;
    }

    private void generateDoctorRecommendations(AiConsultation consultation) {
        List<Doctor> doctors = doctorRepository
        		.findBySpecialization(consultation.getSuggestedSpecialization());

        for (Doctor doctor : doctors) {
            AiDoctorRecommendation recommendation = new AiDoctorRecommendation();
            recommendation.setConsultationId(consultation.getId());
            recommendation.setDoctorId(doctor.getId());
            recommendation.setMatchScore(calculateScore(consultation.getRiskLevel()));
            recommendation.setReason(
                    "Bác sĩ phù hợp với chuyên khoa "
                            + consultation.getSuggestedSpecialization()
                            + " dựa trên triệu chứng bạn đã nhập."
            );

            recommendationRepository.save(recommendation);
        }
    }

    private double calculateScore(String riskLevel) {
        if ("HIGH".equalsIgnoreCase(riskLevel)) {
            return 95.0;
        }
        if ("MEDIUM".equalsIgnoreCase(riskLevel)) {
            return 88.0;
        }
        return 80.0;
    }

    public List<AiConsultation> getHistory(Long patientId) {
        return consultationRepository.findByPatientIdOrderByCreatedAtDesc(patientId);
    }

    public List<Doctor> findRecommendedDoctors(String specialization) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(specialization);
    }
}