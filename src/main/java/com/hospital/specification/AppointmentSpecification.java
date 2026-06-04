package com.hospital.specification;

import com.hospital.dto.AppointmentSearchDTO;
import com.hospital.entity.Appointment;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AppointmentSpecification {

    public static Specification<Appointment> search(
            AppointmentSearchDTO searchDTO) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (searchDTO == null) {
                return criteriaBuilder.conjunction();
            }

            if (searchDTO.getPatientName() != null
                    && !searchDTO.getPatientName().isBlank()) {

                Join<Object, Object> patientJoin = root.join("patient");
                Join<Object, Object> patientUserJoin = patientJoin.join("user");

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

                Join<Object, Object> doctorJoin = root.join("doctor");
                Join<Object, Object> doctorUserJoin = doctorJoin.join("user");

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
                                root.get("appointmentDate"),
                                searchDTO.getFromDate()
                        )
                );
            }

            if (searchDTO.getToDate() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("appointmentDate"),
                                searchDTO.getToDate()
                        )
                );
            }

            if (searchDTO.getStatus() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("status"),
                                searchDTO.getStatus()
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}