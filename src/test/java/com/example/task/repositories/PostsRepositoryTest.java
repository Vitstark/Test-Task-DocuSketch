package com.example.task.repositories;

import com.example.task.models.Post;
import com.example.task.models.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

@DataMongoTest
public class PostsRepositoryTest {
    @Autowired
    private PostsRepository postsRepository;

    private Post post;
    @BeforeEach
    public void initDB() {
        post = Post.builder()
                .text("text")
                .userId(new ObjectId("634c173323dd1359d222e780"))
                .dateOfCreation(LocalDateTime.now())
                .build();
    }

    @AfterEach
    public void tearDown() {
        postsRepository.deleteAll();
    }

    @Test
    public void repositoryShouldFindUser() {
        postsRepository.save(post);
        Post actualPost = postsRepository.findById(post.getId()).get();

        assertEquals(post.getUserId(), actualPost.getUserId());
        assertEquals(post.getText(), actualPost.getText());
    }

    @Test
    public void repositoryShouldDeleteUser() {
        postsRepository.save(post);
        postsRepository.delete(post);

        Optional<Post> pst = postsRepository.findById(post.getId());

        assertTrue(pst.isEmpty());
    }
}