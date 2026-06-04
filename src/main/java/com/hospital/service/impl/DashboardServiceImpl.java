package com.hospital.service.impl;

import com.hospital.repository.*;
import com.hospital.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final DoctorRepository doctorRepository;

    private final PatientRepository patientRepository;

    private final AppointmentRepository appointmentRepository;

    private final MedicalRecordRepository medicalRecordRepository;

    private final UserRepository userRepository;

    @Override
    public long totalDoctors() {
        return doctorRepository.count();
    }

    @Override
    public long totalPatients() {
        return patientRepository.count();
    }

    @Override
    public long totalAppointments() {
        return appointmentRepository.count();
    }

    @Override
    public long totalMedicalRecords() {
        return medicalRecordRepository.count();
    }

    @Override
    public long totalUsers() {
        return userRepository.count();
    }
}