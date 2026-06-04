package com.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_medical_summaries")
public class AiMedicalSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="medical_record_id", nullable=false, unique=true)
    private Long medicalRecordId;

    @Column(name="original_content", columnDefinition="NVARCHAR(MAX)")
    private String originalContent;

    @Column(name="summary", columnDefinition="NVARCHAR(MAX)")
    private String summary;

    @Column(name="key_symptoms", columnDefinition="NVARCHAR(MAX)")
    private String keySymptoms;

    @Column(name="diagnosis_summary", columnDefinition="NVARCHAR(MAX)")
    private String diagnosisSummary;

    @Column(name="treatment_summary", columnDefinition="NVARCHAR(MAX)")
    private String treatmentSummary;

    @Column(name="follow_up_note", columnDefinition="NVARCHAR(MAX)")
    private String followUpNote;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public Long getMedicalRecordId() { return medicalRecordId; }
    public String getOriginalContent() { return originalContent; }
    public String getSummary() { return summary; }
    public String getKeySymptoms() { return keySymptoms; }
    public String getDiagnosisSummary() { return diagnosisSummary; }
    public String getTreatmentSummary() { return treatmentSummary; }
    public String getFollowUpNote() { return followUpNote; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setMedicalRecordId(Long medicalRecordId) { this.medicalRecordId = medicalRecordId; }
    public void setOriginalContent(String originalContent) { this.originalContent = originalContent; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setKeySymptoms(String keySymptoms) { this.keySymptoms = keySymptoms; }
    public void setDiagnosisSummary(String diagnosisSummary) { this.diagnosisSummary = diagnosisSummary; }
    public void setTreatmentSummary(String treatmentSummary) { this.treatmentSummary = treatmentSummary; }
    public void setFollowUpNote(String followUpNote) { this.followUpNote = followUpNote; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}