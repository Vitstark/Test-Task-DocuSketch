package com.example.task.services;

import com.example.task.models.Comment;
import com.example.task.repositories.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentsRepository commentsRepository;

    public void save(Comment comment) {
        commentsRepository.save(comment);
    }

    public Optional<Comment> findById(ObjectId id) {
        return commentsRepository.findById(id);
    }

    public void delete(Comment comment) {
        commentsRepository.delete(comment);
    }
}
