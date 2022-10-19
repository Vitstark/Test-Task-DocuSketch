package com.example.task.controllers;

import com.example.task.models.Post;
import com.example.task.security.UserDetailsImpl;
import com.example.task.services.PostService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;

    @GetMapping("/{id}")
    public String post(@PathVariable("id") ObjectId id, Model model) {
        Optional<Post> postOptional = postService.findById(id);

        if (postOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "post doesn't exists");
        }

        model.addAttribute("post", postOptional.get());
        return "posts/postpage";
    }

    @GetMapping("/new")
    public String createPost() {
        return "posts/newpost";
    }

    @PostMapping
    public String createPostProcessing(@ModelAttribute("text") String text) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        Post post = Post.builder()
                .text(text)
                .userId(userDetails.getUser().getId())
                .dateOfCreation(LocalDateTime.now())
                .build();

        postService.save(post);

        return "redirect:/users/mypage";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") ObjectId id) {
        postService.deleteById(id);
        return "redirect:/users/mypage";
    }
}
