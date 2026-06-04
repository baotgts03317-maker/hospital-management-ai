package com.hospital.dto;

import lombok.Data;

@Data
public class MedicalRecordUpdateDTO {

    private Long medicalRecordId;

    private String symptoms;

    private String diagnosis;

    private String prescription;

    private String doctorNotes;

}