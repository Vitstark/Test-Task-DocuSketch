package com.example.task.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@Document
public class Comment {
    @MongoId
    private ObjectId id;

    @NotBlank(message = "Post message shouldn't be blank")
    private String text;

    @CreatedDate
    private LocalDateTime dateOfCreation;

    private ObjectId postId;

    @CreatedBy
    private ObjectId authorId;
}
