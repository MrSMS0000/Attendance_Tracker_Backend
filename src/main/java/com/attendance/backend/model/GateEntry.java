package com.attendance.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Document(collection = "entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GateEntry {

    @Id
    private ObjectId id;
    private LocalDate date;
    private LocalTime time;
    private Status status;
    private String reason;
    public GateEntry(LocalDate date, LocalTime time, Status status, String reason) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.reason = reason;
    }

}
