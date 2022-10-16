package com.example.task.repositories;

import com.example.task.models.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comment, ObjectId> {
}
