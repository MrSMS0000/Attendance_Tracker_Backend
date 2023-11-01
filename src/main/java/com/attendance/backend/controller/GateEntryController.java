package com.attendance.backend.controller;

import com.attendance.backend.model.GateEntry;
import com.attendance.backend.service.GateEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/entry")
public class GateEntryController {
    @Autowired
    private GateEntryService gateEntryService;

    @PostMapping("/{studentId}")
    public ResponseEntity<GateEntry> addGateEntry(@RequestBody GateEntry gateEntry, @PathVariable Long studentId){
        gateEntry.setTime(LocalTime.now());
        gateEntry.setDate(LocalDate.now());
        return new ResponseEntity<>(gateEntryService.addGateEntry(gateEntry,studentId), HttpStatus.CREATED);
    }
}
