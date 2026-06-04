package com.hospital.controller;

import com.hospital.dto.ProfileUpdateDTO;
import com.hospital.entity.User;
import com.hospital.security.CustomUserDetails;
import com.hospital.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String profile(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,
            Model model){

        model.addAttribute(
                "user",
                userDetails.getUser()
        );

        return "profile/profile";
    }

    @GetMapping("/edit")
    public String editForm(
            @AuthenticationPrincipal
            CustomUserDetails userDetails,
            Model model){

        User user =
                userDetails.getUser();

        ProfileUpdateDTO dto =
                new ProfileUpdateDTO();

        dto.setFullName(user.getFullName());

        dto.setPhone(user.getPhone());

        dto.setAddress(user.getAddress());

        model.addAttribute("profileDTO", dto);

        model.addAttribute("user", user);

        return "profile/edit-profile";
    }

    @PostMapping("/edit")
    public String edit(
            @ModelAttribute("profileDTO")
            ProfileUpdateDTO dto,

            @AuthenticationPrincipal
            CustomUserDetails userDetails){

        userService.updateProfile(
                userDetails.getUser().getId(),
                dto
        );

        return "redirect:/profile";
    }
}