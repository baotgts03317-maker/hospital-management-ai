package com.hospital.service;

import com.hospital.dto.PatientDTO;
import com.hospital.entity.Patient;

import java.util.List;

public interface PatientService {

    Patient create(PatientDTO dto);

    Patient update(
            Long id,
            PatientDTO dto
    );

    Patient findById(Long id);

    List<Patient> findAll();

    void delete(Long id);

}