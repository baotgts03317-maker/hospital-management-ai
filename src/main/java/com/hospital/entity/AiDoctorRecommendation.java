package com.hospital.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ai_doctor_recommendations")
public class AiDoctorRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="consultation_id", nullable=false)
    private Long consultationId;

    @Column(name="doctor_id", nullable=false)
    private Long doctorId;

    @Column(name="match_score")
    private Double matchScore;

    @Column(name="reason", columnDefinition="NVARCHAR(MAX)")
    private String reason;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() { return id; }
    public Long getConsultationId() { return consultationId; }
    public Long getDoctorId() { return doctorId; }
    public Double getMatchScore() { return matchScore; }
    public String getReason() { return reason; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setConsultationId(Long consultationId) { this.consultationId = consultationId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
    public void setReason(String reason) { this.reason = reason; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}