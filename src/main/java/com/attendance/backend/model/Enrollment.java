package com.attendance.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document(collection = "enrollment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
    @DocumentReference
    private Student student;
    @DocumentReference
    private Course course;
}
