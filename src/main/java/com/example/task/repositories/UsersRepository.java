package com.example.task.repositories;

import com.example.task.models.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByEmail(String email);
    List<User> findAllByNameContainsIgnoreCase(String name);
}
