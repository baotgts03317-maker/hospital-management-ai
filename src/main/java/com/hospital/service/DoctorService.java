package com.hospital.service;

import com.hospital.dto.DoctorDTO;
import com.hospital.entity.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor create(DoctorDTO dto);

    Doctor update(
            Long id,
            DoctorDTO dto
    );

    Doctor findById(Long id);

    List<Doctor> findAll();

    void delete(Long id);

}