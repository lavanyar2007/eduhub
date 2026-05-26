package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @GetMapping("/student")
    public ResponseEntity<Student> getStudent() {

        Student student = new Student(1, "Ram", "Kumar");

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("students")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "Ram", "Kumar"));
        studentList.add(new Student(2, "lavi", "cutiee"));
        studentList.add(new Student(3, "jeni", "kuttaa"));
        studentList.add(new Student(4, "karthi", "psycho"));

        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }
}