package com.example.task.controllers;

import com.example.task.models.User;
import com.example.task.security.UserDetailsImpl;
import com.example.task.services.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @GetMapping("/search")
    public String users(Model model, @RequestParam(name = "name", required = false) String name) {
        List<User> users;
        if (name != null && !name.isBlank()) {
            users = userService.findAllByNameContainsIgnoreCase(name);
        } else {
            users = userService.findAll();
        }


        model.addAttribute("users", users);
        return "users/search";
    }

    @GetMapping("/{id}")
    public String user(@PathVariable("id") ObjectId id, Model model) {
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        model.addAttribute("user", userOptional.get());
        return "users/userpage";
    }

    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        ObjectId currentUserId = userDetails.getUser().getId();

        User user = userService.findById(currentUserId).get();

        model.addAttribute("user", user);
        return "users/mypage";
    }
}
