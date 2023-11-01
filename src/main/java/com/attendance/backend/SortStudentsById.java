package com.attendance.backend;

import com.attendance.backend.model.Student;

import java.util.Comparator;

public class SortStudentsById implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return (int) (o1.getId() - o2.getId());
    }
}
