package com.example.task.controllers;

import com.example.task.models.Comment;
import com.example.task.security.UserDetailsImpl;
import com.example.task.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @GetMapping("/new")
    public String createComment(@RequestParam("postId") ObjectId postId, Model model) {
        model.addAttribute("postId", postId);
        return "comments/newcomment";
    }

    @PostMapping
    public String createCommentProcessing(@ModelAttribute("text") String text,
                                          @ModelAttribute("postId") ObjectId postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(userDetails.getUser().getId())
                .text(text)
                .dateOfCreation(LocalDateTime.now())
                .build();

        commentService.save(comment);

        return "redirect:posts/" + postId;
    }
}
