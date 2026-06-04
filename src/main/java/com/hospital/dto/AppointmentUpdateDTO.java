package com.hospital.dto;

import com.hospital.entity.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentUpdateDTO {

    private Long appointmentId;

    private AppointmentStatus status;

    private String reason;

}