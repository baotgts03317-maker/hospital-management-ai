package com.hospital.service.impl;

import com.hospital.dto.DoctorDTO;
import com.hospital.entity.Doctor;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.DoctorRepository;
import com.hospital.service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl
        implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor create(DoctorDTO dto) {

        Doctor doctor = Doctor.builder()
                .specialization(dto.getSpecialization())
                .experience(dto.getExperience())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Long id, DoctorDTO dto) {

        Doctor doctor = findById(id);

        doctor.setSpecialization(
                dto.getSpecialization()
        );

        doctor.setExperience(
                dto.getExperience()
        );

        doctor.setDescription(
                dto.getDescription()
        );

        doctor.setImageUrl(
                dto.getImageUrl()
        );

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor findById(Long id) {

        return doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor không tồn tại"
                        ));
    }

    @Override
    public List<Doctor> findAll() {

        return doctorRepository.findAll();
    }

    @Override
    public void delete(Long id) {

        doctorRepository.deleteById(id);
    }
}