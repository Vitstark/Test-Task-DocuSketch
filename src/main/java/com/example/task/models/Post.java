package com.example.task.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document
public class Post {
    @MongoId
    private ObjectId id;

    @NotBlank(message = "Post message shouldn't be blank")
    private String text;

    @CreatedDate
    private LocalDateTime dateOfCreation;

    private ObjectId userId;

    @ReadOnlyProperty
    private List<Comment> comments;
}
