package com.odetto.controller;

import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list-students")
    public ResponseEntity<List<StudentResponseDTO>> listStudents() {
        List<StudentResponseDTO> students = studentService.listStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/insert-student")
    public ResponseEntity<String> insertStudent(@RequestBody StudentResponseDTO student) {
    public ResponseEntity<String> insertStudent(@RequestBody StudentRequestDTO student) {
        studentService.insertStudent(student);
        return ResponseEntity.ok("Student inserted successfully!");
    }
}
