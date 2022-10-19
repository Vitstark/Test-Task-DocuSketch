package com.example.task.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    private String email;
    private String name;
    private String password;

    @ReadOnlyProperty
    private List<Post> posts;
}

