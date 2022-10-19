package com.example.task.services;

import com.example.task.models.Comment;
import com.example.task.repositories.CommentsRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentsRepository commentsRepository;
    private CommentService commentService;
    private Comment comment = Comment.builder()
            .id(new ObjectId())
            .build();

    @BeforeEach
    void setUp() {
        commentService = new CommentService(commentsRepository);
    }

    @Test
    void save() {
        commentService.save(comment);

        Mockito.verify(commentsRepository)
                .save(comment);
    }

    @Test
    void findById() {
        commentService.findById(comment.getId());

        Mockito.verify(commentsRepository)
                .findById(comment.getId());
    }

    @Test
    void delete() {
        commentService.delete(comment);

        Mockito.verify(commentsRepository)
                .delete(comment);
    }
}