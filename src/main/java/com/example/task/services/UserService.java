package com.example.task.services;

import com.example.task.models.Post;
import com.example.task.models.User;
import com.example.task.repositories.PostsRepository;
import com.example.task.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public Optional<User> findById(ObjectId userId) {
        Optional<User> userOptional = usersRepository.findById(userId);
        initPosts(userOptional);
        return userOptional;
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> userOptional = usersRepository.findByEmail(email);
        initPosts(userOptional);
        return userOptional;
    }

    private void initPosts(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            List<Post> posts = postsRepository.findAllByUserId(userOptional.get().getId());
            userOptional.get().setPosts(posts);
        }
    }

    public void delete(User user) {
        postsRepository.deleteAllByUserId(user.getId());
        usersRepository.delete(user);
    }
}
