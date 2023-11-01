package com.attendance.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Objects;
@EqualsAndHashCode(callSuper = true)
@Document(collection = "faculties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faculty extends Person{
    private String name;
}
