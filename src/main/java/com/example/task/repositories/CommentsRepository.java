package com.example.task.repositories;

import com.example.task.models.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentsRepository extends MongoRepository<Comment, ObjectId> {
    List<Comment> findAllByPostIdOrderByDateOfCreationDesc(ObjectId postId);
    void deleteAllByPostId(ObjectId postId);
}
