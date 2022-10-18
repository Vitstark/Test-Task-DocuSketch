package com.example.task.services;

import com.example.task.models.Post;
import com.example.task.repositories.CommentsRepository;
import com.example.task.repositories.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    public void save(Post post) {
        postsRepository.save(post);
    }

    public Optional<Post> findById(ObjectId id) {
        Optional<Post> post = postsRepository.findById(id);
        if (post.isPresent()) {
            post.get().setComments(commentsRepository.findAllByPostIdOrderByDateOfCreationDesc(id));
        }
        return post;
    }

    public void deleteById(ObjectId id) {
        commentsRepository.deleteAllByPostId(id);
        postsRepository.deleteById(id);
    }

    public void delete(Post post) {
        commentsRepository.deleteAllByPostId(post.getId());
        postsRepository.delete(post);
    }
}
