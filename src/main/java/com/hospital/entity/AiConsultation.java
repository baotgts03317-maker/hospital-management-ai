package com.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_consultations")
public class AiConsultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="patient_id", nullable=false)
    private Long patientId;

    @Column(name="symptoms_input", columnDefinition="NVARCHAR(MAX)")
    private String symptomsInput;

    @Column(name="ai_analysis", columnDefinition="NVARCHAR(MAX)")
    private String aiAnalysis;

    @Column(name="risk_level")
    private String riskLevel;

    @Column(name="suggested_specialization")
    private String suggestedSpecialization;

    @Column(name="advice", columnDefinition="NVARCHAR(MAX)")
    private String advice;

    @Column(name="emergency_warning")
    private Boolean emergencyWarning = false;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getter Setter
    public Long getId() { return id; }
    public Long getPatientId() { return patientId; }
    public String getSymptomsInput() { return symptomsInput; }
    public String getAiAnalysis() { return aiAnalysis; }
    public String getRiskLevel() { return riskLevel; }
    public String getSuggestedSpecialization() { return suggestedSpecialization; }
    public String getAdvice() { return advice; }
    public Boolean getEmergencyWarning() { return emergencyWarning; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    public void setSymptomsInput(String symptomsInput) { this.symptomsInput = symptomsInput; }
    public void setAiAnalysis(String aiAnalysis) { this.aiAnalysis = aiAnalysis; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public void setSuggestedSpecialization(String suggestedSpecialization) { this.suggestedSpecialization = suggestedSpecialization; }
    public void setAdvice(String advice) { this.advice = advice; }
    public void setEmergencyWarning(Boolean emergencyWarning) { this.emergencyWarning = emergencyWarning; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}