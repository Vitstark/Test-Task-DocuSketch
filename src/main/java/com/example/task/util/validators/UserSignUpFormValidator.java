package com.example.task.util.validators;

import com.example.task.dto.UserSignUpForm;
import com.example.task.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserSignUpFormValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserSignUpForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserSignUpForm form = (UserSignUpForm) target;

        if (userService.findByEmail(form.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "User with your email already exists");
        }
    }
}
