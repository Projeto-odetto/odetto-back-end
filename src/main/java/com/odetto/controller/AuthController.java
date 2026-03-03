package com.odetto.controller;

import com.odetto.dto.LoginRequestDTO;
import com.odetto.dto.Student.StudentLoginResponseDTO;
import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.model.Student;
import com.odetto.model.Teacher;
import com.odetto.service.StudentService;
import com.odetto.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TeacherService teacherService;
    private final StudentService studentService;

    public AuthController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        Long cpf = request.getCpf();
        String password = request.getPassword();

        Optional<TeacherResponseDTO> teacherOpt = teacherService.getTeacher(cpf);
        if (teacherOpt.isPresent()) {
            TeacherResponseDTO teacher = teacherOpt.get();
            if (teacher.getPassword() == null || !teacher.getPassword().equals(password)) {
                return ResponseEntity.status(401).body("Senha incorreta para professor.");
            }
            return ResponseEntity.ok(teacher);
        }

        Optional<Student> studentOpt = studentService.findStudentByCpf(cpf);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }

        Student student = studentOpt.get();
        if (student.getPassword() == null || !student.getPassword().equals(password)) {
            return ResponseEntity.status(401).body("Senha incorreta para aluno.");
        }

        boolean firstLogin = (student.getName() == null || student.getName().isBlank());
        StudentLoginResponseDTO resp = new StudentLoginResponseDTO(
                student.getEnrollment(),
                student.getName(),
                student.getEmail(),
                student.getPassword(),
                student.getCpf(),
                firstLogin
        );

        return ResponseEntity.ok(resp);
    }
}