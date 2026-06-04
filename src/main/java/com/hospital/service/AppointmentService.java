package com.hospital.service;

import com.hospital.dto.AppointmentCreateDTO;
import com.hospital.dto.AppointmentSearchDTO;
import com.hospital.entity.Appointment;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(AppointmentCreateDTO dto);

    Appointment findById(Long id);

    void cancelAppointment(Long id);

    Appointment confirmAppointment(Long id);

    Appointment completeAppointment(Long id);

    Page<Appointment> search(
            AppointmentSearchDTO dto,
            Pageable pageable
    );

    List<Appointment> findByPatient(Patient patient);

    List<Appointment> findByDoctor(Doctor doctor);
}