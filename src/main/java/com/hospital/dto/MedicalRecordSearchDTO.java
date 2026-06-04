package com.hospital.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalRecordSearchDTO {

    private String patientName;

    private String doctorName;

    private LocalDate fromDate;

    private LocalDate toDate;

}