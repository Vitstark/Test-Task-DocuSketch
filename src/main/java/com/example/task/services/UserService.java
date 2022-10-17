package com.example.task.services;

import com.example.task.models.Post;
import com.example.task.models.User;
import com.example.task.repositories.PostsRepository;
import com.example.task.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;

    public void save(User user) {
        usersRepository.save(user);
    }

    public Optional<User> findById(ObjectId userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Post> posts = postsRepository.findAllByUserId(userId);
            userOptional.get().setPosts(posts);
        }
        return userOptional;
    }

    public void delete(User user) {
        postsRepository.deleteAllByUserId(user.getId());
        usersRepository.delete(user);
    }
}
