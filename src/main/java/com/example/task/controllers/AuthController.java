package com.example.task.controllers;

import com.example.task.dto.UserSignUpForm;
import com.example.task.models.User;
import com.example.task.services.UserService;
import com.example.task.util.validators.UserSignUpFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserSignUpFormValidator validator;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String register(@ModelAttribute("form") UserSignUpForm form) {
        return "auth/register";
    }

    @PostMapping("/registration")
    public String registerProcessing(@ModelAttribute("form") @Valid UserSignUpForm form,
                                     Errors errors) {
        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return "auth/register";
        }

        User user = User.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .build();

        userService.save(user);

        return "redirect:/users/mypage";
    }
}
