package com.attendance.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;

@Document(collection = "course_attendance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseAttendance {
    @DocumentReference
    private Course course;
    private LocalDate date;
}
