package com.example.task.services;

import com.example.task.models.User;
import com.example.task.repositories.PostsRepository;
import com.example.task.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private PostsRepository postsRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(usersRepository, postsRepository, new BCryptPasswordEncoder());
    }

    @Test
    public void mockTest() {
        User user = User.builder()
                .password("1234")
                .build();
        userService.save(user);
        Mockito.verify(usersRepository).save(user);
    }
}
