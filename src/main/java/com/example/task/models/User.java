package com.example.task.models;

import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.*;

import javax.validation.constraints.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @MongoId
    private ObjectId id;

    @Email(message = "Email should be correct")
    @Indexed(unique = true)
    private String email;

    @NotBlank(message = "Name should be not blank")
    @Size(min = 1, max = 30, message = "Name length should be between 1 and 30")
    private String name;

    private String password;

    @ReadOnlyProperty
    private List<Post> posts;
}

