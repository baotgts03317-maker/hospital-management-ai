package com.hospital.repository;

import com.hospital.entity.AiMedicalSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AiMedicalSummaryRepository extends JpaRepository<AiMedicalSummary, Long> {
    Optional<AiMedicalSummary> findByMedicalRecordId(Long medicalRecordId);
}