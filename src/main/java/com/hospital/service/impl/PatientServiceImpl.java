package com.hospital.service.impl;

import com.hospital.dto.PatientDTO;
import com.hospital.entity.Patient;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.PatientRepository;
import com.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient create(PatientDTO dto) {
        Patient patient = Patient.builder()
                .gender(dto.getGender())
                .age(dto.getAge())
                .birthDate(dto.getBirthDate())
                .bloodGroup(dto.getBloodGroup())
                .build();

        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Long id, PatientDTO dto) {
        Patient patient = findById(id);

        patient.setGender(dto.getGender());
        patient.setAge(dto.getAge());
        patient.setBirthDate(dto.getBirthDate());
        patient.setBloodGroup(dto.getBloodGroup());

        return patientRepository.save(patient);
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy bệnh nhân"));
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Patient patient = findById(id);
        patientRepository.delete(patient);
    }
}
