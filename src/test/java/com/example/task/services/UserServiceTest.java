package com.example.task.services;

import com.example.task.models.Post;
import com.example.task.models.User;
import com.example.task.repositories.PostsRepository;
import com.example.task.repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class UserServiceTest {
    private UserService userService;
    private UsersRepository usersRepository;
    private PostsRepository postsRepository;

    private User user;
    private Post post1;
    private Post post2;

    @Autowired
    public UserServiceTest(UsersRepository usersRepository, PostsRepository postsRepository) {
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
        userService = new UserService(usersRepository, postsRepository, new BCryptPasswordEncoder());
    }

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email("asdfh@mail.ru")
                .name("asdfasdf")
                .password("*****")
                .build();

        post1 = Post.builder()
                .text("post1")
                .dateOfCreation(LocalDateTime.now())
                .build();

        post2 = Post.builder()
                .text("post2")
                .dateOfCreation(LocalDateTime.now())
                .build();
    }

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
        postsRepository.deleteAll();
    }

    private void saveAll() {
        userService.save(user);

        post1.setUserId(user.getId());
        post2.setUserId(new ObjectId());

        postsRepository.save(post1);
        postsRepository.save(post2);
    }

    @Test
    void findByIdTest() {
        saveAll();
        User actualUser = userService.findById(user.getId()).get();

        List<Post> posts = actualUser.getPosts();
        assertEquals(1, posts.size());

        Post post = posts.get(0);
        assertEquals(post1.getId(), post.getId());
        assertEquals(post1.getText(), post.getText());
    }

    @Test
    void deleteTest() {
        saveAll();
        userService.delete(user);

        Optional<Post> postOptional = postsRepository.findById(post2.getId());
        assertTrue(postOptional.isPresent());
        assertTrue(postsRepository.findById(post1.getId()).isEmpty());

        Post post = postOptional.get();
        assertEquals(post2.getText(), post.getText());
    }
}