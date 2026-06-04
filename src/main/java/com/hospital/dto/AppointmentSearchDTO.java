package com.hospital.dto;

import com.hospital.entity.AppointmentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentSearchDTO {

    private String patientName;

    private String doctorName;

    private LocalDate fromDate;

    private LocalDate toDate;

    private AppointmentStatus status;

}