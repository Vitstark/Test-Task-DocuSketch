package com.example.task.services;

import com.example.task.models.Comment;
import com.example.task.models.Post;
import com.example.task.models.User;
import com.example.task.repositories.CommentsRepository;
import com.example.task.repositories.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    public void save(Post post) {
        postsRepository.save(post);
    }

    public Optional<Post> findById(ObjectId id) {
        Optional<Post> post = postsRepository.findById(id);
        if (post.isPresent()) {
            post.get().setComments(commentsRepository.findAllByPostId(id));
        }
        return post;
    }

    public void delete(Post post) {
        commentsRepository.deleteAllByPostId(post.getId());
        postsRepository.delete(post);
    }
}
