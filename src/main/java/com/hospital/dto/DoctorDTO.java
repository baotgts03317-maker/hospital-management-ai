package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorDTO {

    private Long id;

    @NotBlank
    private String specialization;

    @NotNull
    private Integer experience;

    private String description;

    private String imageUrl;
}