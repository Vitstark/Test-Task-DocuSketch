package com.example.task.services;

import com.example.task.models.Post;
import com.example.task.repositories.CommentsRepository;
import com.example.task.repositories.PostsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostServiceMockTest {
    @Mock
    private PostsRepository postsRepository;
    @Mock
    private CommentsRepository commentsRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postService = new PostService(postsRepository, commentsRepository);
    }

    @Test
    void save() {
        Post post = Post.builder().build();

        postService.save(post);

        Mockito.verify(postsRepository).save(post);
    }
}