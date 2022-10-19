package com.example.task.repositories;

import com.example.task.models.Comment;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CommentsRepositoryTest {
    @Autowired
    private CommentsRepository commentsRepository;

    private Comment comment;

    @BeforeEach
    public void initDB() {
        comment = Comment.builder()
                .text("qwert")
                .dateOfCreation(LocalDateTime.now())
                .authorId(new ObjectId())
                .postId(new ObjectId())
                .build();
    }

    @AfterEach
    public void tearDown() {
        commentsRepository.deleteAll();
    }

    @Test
    public void repositoryShouldReturnComment() {
        commentsRepository.save(comment);
        Comment actualComment = commentsRepository.findById(comment.getId()).get();

        assertEquals(comment.getText(), actualComment.getText());
        assertEquals(comment.getPostId(), actualComment.getPostId());
        assertEquals(comment.getAuthorId(), actualComment.getAuthorId());
    }

    @Test
    public void repositoryShouldDeleteComment() {
        commentsRepository.save(comment);
        commentsRepository.delete(comment);
        Optional<Comment> com = commentsRepository.findById(comment.getId());

        assertTrue(com.isEmpty());
    }
}