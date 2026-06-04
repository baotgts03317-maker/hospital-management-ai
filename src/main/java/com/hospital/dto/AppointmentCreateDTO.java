package com.hospital.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentCreateDTO {

    @NotNull
    private Long doctorId;

    @NotNull
    private Long patientId;

    @FutureOrPresent(
            message = "Không được chọn ngày trong quá khứ")
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime appointmentTime;

    private String reason;
}