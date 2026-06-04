package com.hospital.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileUpdateDTO {

    private String fullName;

    private String phone;

    private String address;

    private MultipartFile image;
}