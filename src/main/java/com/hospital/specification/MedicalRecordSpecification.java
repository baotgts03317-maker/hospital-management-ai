package com.hospital.specification;

import com.hospital.dto.MedicalRecordSearchDTO;
import com.hospital.entity.MedicalRecord;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordSpecification {

    public static Specification<MedicalRecord> search(
            MedicalRecordSearchDTO searchDTO) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (searchDTO == null) {
                return criteriaBuilder.conjunction();
            }

            Join<Object, Object> appointmentJoin =
                    root.join("appointment");

            if (searchDTO.getPatientName() != null
                    && !searchDTO.getPatientName().isBlank()) {

                Join<Object, Object> patientJoin =
                        appointmentJoin.join("patient");

                Join<Object, Object> patientUserJoin =
                        patientJoin.join("user");

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        patientUserJoin.get("fullName")
                                ),
                                "%" + searchDTO.getPatientName()
                                        .toLowerCase() + "%"
                        )
                );
            }

            if (searchDTO.getDoctorName() != null
                    && !searchDTO.getDoctorName().isBlank()) {

                Join<Object, Object> doctorJoin =
                        appointmentJoin.join("doctor");

                Join<Object, Object> doctorUserJoin =
                        doctorJoin.join("user");

                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        doctorUserJoin.get("fullName")
                                ),
                                "%" + searchDTO.getDoctorName()
                                        .toLowerCase() + "%"
                        )
                );
            }

            if (searchDTO.getFromDate() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("createdAt"),
                                searchDTO.getFromDate().atStartOfDay()
                        )
                );
            }

            if (searchDTO.getToDate() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("createdAt"),
                                searchDTO.getToDate().atTime(23, 59, 59)
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}