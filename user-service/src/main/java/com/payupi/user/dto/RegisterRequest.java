package com.payupi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "full name is required")
    private String fullName;

    @Email(message =
            "Invalid email format")
    @NotBlank(message =
            "Email is required")
    private String email;


    private String registrationId;





}