package com.hospital.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(
            ResourceNotFoundException ex,
            Model model) {

        model.addAttribute("errorTitle", "Không tìm thấy dữ liệu");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            Model model) {

        model.addAttribute("errorTitle", "Email đã tồn tại");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(AppointmentConflictException.class)
    public String handleAppointmentConflict(
            AppointmentConflictException ex,
            Model model) {

        model.addAttribute("errorTitle", "Trùng lịch khám");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(
            BadRequestException ex,
            Model model) {

        model.addAttribute("errorTitle", "Yêu cầu không hợp lệ");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(
            Exception ex,
            Model model) {

        model.addAttribute("errorTitle", "Lỗi hệ thống");
        model.addAttribute("errorMessage", ex.getMessage());

        return "error/error";
    }
}