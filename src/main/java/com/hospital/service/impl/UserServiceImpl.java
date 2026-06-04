package com.hospital.service.impl;

import com.hospital.dto.ChangePasswordDTO;
import com.hospital.dto.RegisterDTO;
import com.hospital.entity.Role;
import com.hospital.entity.User;
import com.hospital.exception.EmailAlreadyExistsException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.repository.UserRepository;
import com.hospital.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepository;
import com.hospital.dto.ProfileUpdateDTO;
import com.hospital.util.FileUploadUtil;
@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PatientRepository patientRepository;
    @Override
    public User register(RegisterDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("Email đã tồn tại");
        }

        User user = User.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .enabled(true)
                .role(Role.ROLE_PATIENT)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        Patient patient = Patient.builder()
                .user(savedUser)
                .build();

        patientRepository.save(patient);

        return savedUser;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy User"
                        ));
    }

    @Override
    public List<User> findAll() {

        return userRepository.findAll();
    }

    @Override
    public User save(User user) {

        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public void changePassword(
            Long userId,
            ChangePasswordDTO dto) {

        User user = findById(userId);

        user.setPassword(
                passwordEncoder.encode(
                        dto.getNewPassword()
                )
        );

        userRepository.save(user);
    }

    @Override
    public void lockUser(Long id) {

        User user = findById(id);

        user.setEnabled(false);

        userRepository.save(user);
    }

    @Override
    public void unlockUser(Long id) {

        User user = findById(id);

        user.setEnabled(true);

        userRepository.save(user);
    }
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Override
    public User updateProfile(
            Long userId,
            ProfileUpdateDTO dto) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow();

        user.setFullName(dto.getFullName());

        user.setPhone(dto.getPhone());

        user.setAddress(dto.getAddress());

        try{

            if(dto.getImage() != null
                    && !dto.getImage().isEmpty()){

                String fileName =
                        FileUploadUtil.saveFile(
                                uploadDir,
                                dto.getImage()
                        );

                user.setAvatar(fileName);

            }

        }catch (Exception e){

            e.printStackTrace();

        }

        return userRepository.save(user);
    }
}
