package com.example.task.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Document("comments")
public class Comment {
    @MongoId
    private ObjectId id;

    @NotBlank(message = "Post message shouldn't be blank")
    private String text;

    private LocalDateTime dateOfCreation;

    private ObjectId postId;
    private ObjectId authorId;
}
