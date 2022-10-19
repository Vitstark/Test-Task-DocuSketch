package com.example.task.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserSignUpForm {
    @Email(message = "Email must be correct")
    private String email;
    @NotBlank(message = "Name should be not blank")
    @Size(min = 1, max = 30, message = "Name length should be between 1 and 30")
    private String name;
    private String password;
}
