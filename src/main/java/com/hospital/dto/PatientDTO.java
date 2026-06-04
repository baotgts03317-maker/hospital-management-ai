package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {

    private Long id;

    @NotBlank
    private String gender;

    private Integer age;

    private LocalDate birthDate;

    private String bloodGroup;
}