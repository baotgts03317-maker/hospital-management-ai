package com.hospital.service;

import com.hospital.dto.ChangePasswordDTO;
import com.hospital.dto.ProfileUpdateDTO;
import com.hospital.dto.RegisterDTO;
import com.hospital.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(RegisterDTO dto);

    Optional<User> findByEmail(String email);

    User findById(Long id);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

    void changePassword(
            Long userId,
            ChangePasswordDTO dto
    );

    void lockUser(Long id);

    void unlockUser(Long id);
    User updateProfile(
            Long userId,
            ProfileUpdateDTO dto
    );
}