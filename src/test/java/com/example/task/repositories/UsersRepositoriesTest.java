package com.example.task.repositories;

import com.example.task.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class UsersRepositoriesTest {
    @Autowired
    private UsersRepository usersRepository;

    private User user;

    @BeforeEach
    public void initDB() {
        user = User.builder()
                .email("asdf@gmail.com")
                .name("hasdf")
                .password("qwerty12345")
                .build();
    }

    @AfterEach
    public void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    public void repositoryShouldReturnUser() {
        usersRepository.save(user);
        User actualUser = usersRepository.findById(user.getId()).get();

        assertEquals(user.getEmail(), actualUser.getEmail());
        assertEquals(user.getName(), actualUser.getName());
        assertEquals(user.getPassword(), actualUser.getPassword());
    }

    @Test
    public void repositoryShouldDeleteUser() {
        usersRepository.save(user);
        usersRepository.delete(user);

        Optional<User> usr = usersRepository.findById(user.getId());

        assertTrue(usr.isEmpty());
    }
}
