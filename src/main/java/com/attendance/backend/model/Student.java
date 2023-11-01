package com.attendance.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "students")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Student extends Person{
    private String name;
    @DocumentReference
    private List<GateEntry> gateAttendanceHistory;
}
