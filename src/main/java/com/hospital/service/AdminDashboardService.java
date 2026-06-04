package com.hospital.service;

import com.hospital.entity.AppointmentStatus;
import com.hospital.repository.AiConsultationRepository;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final AiConsultationRepository aiConsultationRepository;

    public AdminDashboardService(
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository,
            AiConsultationRepository aiConsultationRepository
    ) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.aiConsultationRepository = aiConsultationRepository;
    }

    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();

        long totalPatients = patientRepository.count();
        long totalDoctors = doctorRepository.count();
        long totalAppointments = appointmentRepository.count();
        long totalAiConsultations = aiConsultationRepository.count();

        long lowRisk = aiConsultationRepository.countByRiskLevel("LOW");
        long mediumRisk = aiConsultationRepository.countByRiskLevel("MEDIUM");
        long highRisk = aiConsultationRepository.countByRiskLevel("HIGH");

        long pendingAppointments = appointmentRepository.countByStatus(AppointmentStatus.PENDING);
        long confirmedAppointments = appointmentRepository.countByStatus(AppointmentStatus.CONFIRMED);
        long completedAppointments = appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);

        data.put("totalPatients", totalPatients);
        data.put("totalDoctors", totalDoctors);
        data.put("totalAppointments", totalAppointments);
        data.put("totalAiConsultations", totalAiConsultations);

        data.put("lowRisk", lowRisk);
        data.put("mediumRisk", mediumRisk);
        data.put("highRisk", highRisk);

        data.put("pendingAppointments", pendingAppointments);
        data.put("confirmedAppointments", confirmedAppointments);
        data.put("completedAppointments", completedAppointments);

        data.put("aiInsight", generateAiInsight(lowRisk, mediumRisk, highRisk, totalAiConsultations));

        return data;
    }

    private String generateAiInsight(long low, long medium, long high, long total) {
        if (total == 0) {
            return "Chưa có đủ dữ liệu AI để phân tích xu hướng sức khỏe.";
        }

        if (high > 0) {
            return "AI phát hiện có bệnh nhân thuộc nhóm nguy cơ cao. Admin nên ưu tiên theo dõi và hỗ trợ đặt lịch khám sớm.";
        }

        if (medium >= low) {
            return "Số ca nguy cơ trung bình đang tăng. Hệ thống gợi ý tăng số lịch khám Nội tổng quát trong tuần này.";
        }

        return "Phần lớn lượt tư vấn AI thuộc nhóm nguy cơ thấp. Hệ thống đang hoạt động ổn định.";
    }
}