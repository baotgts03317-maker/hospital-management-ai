package com.hospital.dto;

import com.hospital.validation.PasswordMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@PasswordMatch
public class RegisterDTO {

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Size(min = 6, max = 100,
            message = "Mật khẩu phải từ 6 ký tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu")
    private String confirmPassword;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    private String address;
}