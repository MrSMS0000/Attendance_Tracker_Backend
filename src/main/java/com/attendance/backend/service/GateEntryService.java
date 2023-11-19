package com.attendance.backend.service;

import com.attendance.backend.model.GateEntry;
import com.attendance.backend.model.Pin;
import com.attendance.backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

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

    public String getPin(){
        return template.findById("1", Pin.class,"pin").getPin();
    }

    @Scheduled(fixedRate = 60000)
    public void updatePin(){

        Random rnd = new Random();
        String alphabets = "abcdefghijklmnopqrstuvxyz";
        StringBuilder pinBuffer = new StringBuilder(3);
        for(int i=0; i<3; i++){
            char c = (char) ('a' + rnd.nextInt(26));
            pinBuffer.append(c);
        }
        String pin = pinBuffer.toString();

        Pin oldPin = template.findById("1", Pin.class,"pin");
        oldPin.setPin(pin);
        template.save(oldPin);
    }
}
