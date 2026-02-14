package com.odetto.controller;

<<<<<<< HEAD
=======
import com.odetto.dto.Student.StudentRequestDTO;
>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
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

<<<<<<< HEAD
    @Autowired
=======
>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list-students")
    public ResponseEntity<List<StudentResponseDTO>> listStudents() {
        List<StudentResponseDTO> students = studentService.listStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/insert-student")
<<<<<<< HEAD
    public ResponseEntity<String> insertStudent(@RequestBody StudentResponseDTO student) {
=======
    public ResponseEntity<String> insertStudent(@RequestBody StudentRequestDTO student) {
>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
        studentService.insertStudent(student);
        return ResponseEntity.ok("Student inserted successfully!");
    }
}
