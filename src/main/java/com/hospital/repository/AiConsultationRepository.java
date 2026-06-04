package com.hospital.repository;

import com.hospital.entity.AiConsultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiConsultationRepository extends JpaRepository<AiConsultation, Long> {

    List<AiConsultation> findByPatientIdOrderByCreatedAtDesc(Long patientId);

    long countByRiskLevel(String riskLevel);
}