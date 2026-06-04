package com.hospital.repository;

import com.hospital.entity.AiDoctorRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiDoctorRecommendationRepository extends JpaRepository<AiDoctorRecommendation, Long> {
    List<AiDoctorRecommendation> findByConsultationId(Long consultationId);
}