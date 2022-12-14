package com.example.task.repositories;

import com.example.task.models.Post;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends MongoRepository<Post, ObjectId> {
    List<Post> findAllByUserIdOrderByDateOfCreationDesc(ObjectId userId);
    void deleteAllByUserId(ObjectId userId);
}
