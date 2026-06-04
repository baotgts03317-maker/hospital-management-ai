package com.hospital.repository;

import com.hospital.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long>,
        JpaSpecificationExecutor<Appointment> {

	boolean existsByDoctorAndAppointmentDate(
	        Doctor doctor,
	        LocalDate appointmentDate
	);

    List<Appointment> findByPatientOrderByAppointmentDateDesc(Patient patient);

    List<Appointment> findByDoctorOrderByAppointmentDateDesc(Doctor doctor);
    
    long countByStatus(com.hospital.entity.AppointmentStatus status);
}