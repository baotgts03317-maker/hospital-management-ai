package com.hospital.service.impl;

import com.hospital.dto.MedicalRecordCreateDTO;
import com.hospital.dto.MedicalRecordSearchDTO;
import com.hospital.dto.MedicalRecordUpdateDTO;
import com.hospital.entity.Appointment;
import com.hospital.entity.AppointmentStatus;
import com.hospital.entity.MedicalRecord;
import com.hospital.exception.BadRequestException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.service.MedicalRecordService;
import com.hospital.specification.MedicalRecordSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public MedicalRecord create(MedicalRecordCreateDTO dto) {

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy lịch khám"));

        if (medicalRecordRepository.existsByAppointment(appointment)) {
            throw new BadRequestException("Lịch khám này đã có bệnh án");
        }

        if (appointment.getStatus() != AppointmentStatus.CONFIRMED
                && appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Chỉ tạo bệnh án cho lịch đã xác nhận hoặc hoàn thành");
        }

        MedicalRecord record = MedicalRecord.builder()
                .appointment(appointment)
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .symptoms(dto.getSymptoms())
                .diagnosis(dto.getDiagnosis())
                .prescription(dto.getPrescription())
                .doctorNotes(dto.getDoctorNotes())
                .createdAt(LocalDateTime.now())
                .build();

        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return medicalRecordRepository.save(record);
    }

    @Override
    public MedicalRecord update(Long id, MedicalRecordUpdateDTO dto) {

        MedicalRecord record = findById(id);

        record.setSymptoms(dto.getSymptoms());
        record.setDiagnosis(dto.getDiagnosis());
        record.setPrescription(dto.getPrescription());
        record.setDoctorNotes(dto.getDoctorNotes());

        return medicalRecordRepository.save(record);
    }

    @Override
    public MedicalRecord findById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy bệnh án"));
    }

    @Override
    public void delete(Long id) {
        MedicalRecord record = findById(id);
        medicalRecordRepository.delete(record);
    }

    @Override
    public Page<MedicalRecord> search(
            MedicalRecordSearchDTO dto,
            Pageable pageable) {

        return medicalRecordRepository.findAll(
                MedicalRecordSpecification.search(dto),
                pageable
        );
    }
}