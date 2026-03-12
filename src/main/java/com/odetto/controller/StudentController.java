package com.odetto.controller;

import com.odetto.dto.Student.StudentEditRequestDTO;
import com.odetto.dto.Student.StudentEditSubjectsDTO;
import com.odetto.dto.Student.StudentFinalCadastroDTO;
import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/find-students-by-subject/{subjectName}")
    public ResponseEntity<List<StudentResponseDTO>> findStudentsBySubjectName(@PathVariable String subjectName) {
        List<StudentResponseDTO> students = studentService.findStudentsBySubjectName(subjectName);
        return ResponseEntity.ok(students);
    }

    @PatchMapping("/cadastro-final")
    public ResponseEntity<StudentResponseDTO> finalCadastro(@RequestBody StudentFinalCadastroDTO dto) {
        StudentResponseDTO response = studentService.finalCadastro(dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{enrollment}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long enrollment) {
        studentService.deleteStudent(enrollment);
        return ResponseEntity.ok("Aluno deletado com sucesso.");
    }

    @PatchMapping("/edit")
    public ResponseEntity<StudentResponseDTO> editStudent(@RequestBody StudentEditRequestDTO dto) {
        return ResponseEntity.ok(studentService.editStudent(dto));
    }

    @PatchMapping("/edit-subjects")
    public ResponseEntity<String> editStudentSubjects(@RequestBody StudentEditSubjectsDTO dto) {
        studentService.editStudentSubjects(dto);
        return ResponseEntity.ok("Matérias atualizadas.");
    }
}