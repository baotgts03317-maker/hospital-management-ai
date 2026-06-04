package com.hospital.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordCreateDTO {

    @NotNull
    private Long appointmentId;

    private String symptoms;

    private String diagnosis;

    private String prescription;

    private String doctorNotes;

}