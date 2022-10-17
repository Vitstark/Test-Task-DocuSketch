package com.example.task.services;

import com.example.task.models.Comment;
import com.example.task.models.Post;
import com.example.task.repositories.CommentsRepository;
import com.example.task.repositories.PostsRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@DataMongoTest
public class PostServiceTest {
    private PostService postService;
    private PostsRepository postsRepository;
    private CommentsRepository commentsRepository;

    @Autowired
    public PostServiceTest(PostsRepository postsRepository, CommentsRepository commentsRepository) {
        postService = new PostService(postsRepository, commentsRepository);
        this.commentsRepository = commentsRepository;
        this.postsRepository = postsRepository;
    }

    private Post post;
    private Comment comment1;
    private Comment comment2;

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .text("text")
                .dateOfCreation(LocalDateTime.now())
                .userId(new ObjectId())
                .build();

        comment1 = Comment.builder()
                .text("c1")
                .dateOfCreation(LocalDateTime.now())
                .build();

        comment2 = Comment.builder()
                .text("c2")
                .dateOfCreation(LocalDateTime.now())
                .build();
    }

    @AfterEach
    public void turnDown() {
        postsRepository.deleteAll();
    }

    private void saveAll() {
        postService.save(post);

        comment1.setPostId(post.getId());
        comment2.setPostId(new ObjectId());

        commentsRepository.save(comment1);
        commentsRepository.save(comment2);
    }

    @Test
    public void findShouldReturnComments() {
        saveAll();
        Post actualPost = postService.findById(post.getId()).get();

        List<Comment> comments = actualPost.getComments();
        assertEquals(1, comments.size());

        Comment comment = comments.get(0);
        assertEquals(comment1.getId(), comment.getId());
        assertEquals(comment1.getText(), comment.getText());
    }

    @Test
    public void deleteShouldReturnCorrectComments() {
        saveAll();
        postService.delete(post);

        Optional<Comment> commentOptional = commentsRepository.findById(comment2.getId());
        assertTrue(commentOptional.isPresent());

        Comment comment = commentOptional.get();
        assertEquals(comment.getText(), comment2.getText());
    }
}
