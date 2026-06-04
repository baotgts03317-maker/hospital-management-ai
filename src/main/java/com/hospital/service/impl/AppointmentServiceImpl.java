package com.hospital.service.impl;

import com.hospital.dto.AppointmentCreateDTO;
import com.hospital.dto.AppointmentSearchDTO;
import com.hospital.entity.Appointment;
import com.hospital.entity.AppointmentStatus;
import com.hospital.entity.Doctor;
import com.hospital.entity.Patient;
import com.hospital.exception.AppointmentConflictException;
import com.hospital.exception.BadRequestException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.service.AppointmentService;
import com.hospital.specification.AppointmentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public Appointment bookAppointment(AppointmentCreateDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy bác sĩ"));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy bệnh nhân"));

        if (dto.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Không được đặt lịch trong quá khứ");
        }

        boolean exists = appointmentRepository
                .existsByDoctorAndAppointmentDate(
                        doctor,
                        dto.getAppointmentDate()
                );

        if (exists) {
            throw new AppointmentConflictException(
                    "Bác sĩ đã có lịch khám vào thời gian này"
            );
        }

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmentDate(dto.getAppointmentDate())
                .appointmentTime(dto.getAppointmentTime())
                .reason(dto.getReason())
                .status(AppointmentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy lịch khám"));
    }

    @Override
    public void cancelAppointment(Long id) {
        Appointment appointment = findById(id);

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Không thể hủy lịch đã hoàn thành");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public Appointment confirmAppointment(Long id) {
        Appointment appointment = findById(id);

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new BadRequestException("Chỉ có thể xác nhận lịch đang chờ");
        }

        appointment.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment completeAppointment(Long id) {
        Appointment appointment = findById(id);

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED) {
            throw new BadRequestException("Chỉ có thể hoàn thành lịch đã xác nhận");
        }

        appointment.setStatus(AppointmentStatus.COMPLETED);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Page<Appointment> search(
            AppointmentSearchDTO dto,
            Pageable pageable) {

        return appointmentRepository.findAll(
                AppointmentSpecification.search(dto),
                pageable
        );
    }
    @Override
    public List<Appointment> findByPatient(Patient patient) {
        return appointmentRepository
                .findByPatientOrderByAppointmentDateDesc(patient);
    }

    @Override
    public List<Appointment> findByDoctor(Doctor doctor) {
        return appointmentRepository
                .findByDoctorOrderByAppointmentDateDesc(doctor);
    }
}