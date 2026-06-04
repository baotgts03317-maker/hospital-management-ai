package com.hospital.repository;

import com.hospital.entity.Appointment;
import com.hospital.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>,
        JpaSpecificationExecutor<MedicalRecord> {

    boolean existsByAppointment(Appointment appointment);
}