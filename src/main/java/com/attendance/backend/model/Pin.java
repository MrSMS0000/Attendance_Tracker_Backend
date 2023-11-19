package com.attendance.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "pin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pin {
    @MongoId
    private String id;
    private String pin;
}