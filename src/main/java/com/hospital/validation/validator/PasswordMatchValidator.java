package com.hospital.validation.validator;

import com.hospital.dto.RegisterDTO;
import com.hospital.validation.PasswordMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator
        implements ConstraintValidator<PasswordMatch,
        RegisterDTO> {

    @Override
    public boolean isValid(
            RegisterDTO dto,
            ConstraintValidatorContext context) {

        if (dto == null) {
            return true;
        }

        return dto.getPassword()
                .equals(dto.getConfirmPassword());
    }
}