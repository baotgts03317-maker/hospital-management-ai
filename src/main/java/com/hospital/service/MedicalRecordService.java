package com.hospital.service;

import com.hospital.dto.MedicalRecordCreateDTO;
import com.hospital.dto.MedicalRecordSearchDTO;
import com.hospital.dto.MedicalRecordUpdateDTO;
import com.hospital.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicalRecordService {

    MedicalRecord create(MedicalRecordCreateDTO dto);

    MedicalRecord update(Long id, MedicalRecordUpdateDTO dto);

    MedicalRecord findById(Long id);

    void delete(Long id);

    Page<MedicalRecord> search(
            MedicalRecordSearchDTO dto,
            Pageable pageable
    );
}