package com.attendance.backend.service;

import com.attendance.backend.model.GateEntry;
import com.attendance.backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class GateEntryService {
    @Autowired
    private MongoTemplate template;
    public GateEntry addGateEntry(GateEntry entry, Long studentId){
        template.save(entry, "entries");
        template.updateFirst(Query.query(Criteria.where("_id").is(studentId)),
                new Update().push("gateAttendanceHistory", entry),
                Student.class, "students");
        return entry;
    }
}
