package com.odetto.controller;

import com.odetto.dto.LoginRequestDTO;
import com.odetto.dto.Student.StudentLoginResponseDTO;
import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.model.Student;
import com.odetto.projection.TeacherProjection;
import com.odetto.repository.TeacherRepository;
import com.odetto.service.StudentService;
import com.odetto.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final TeacherRepository teacherRepository;

    public AuthController(TeacherService teacherService, StudentService studentService, TeacherRepository teacherRepository) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.teacherRepository = teacherRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        Long cpf = request.getCpf();
        String password = request.getPassword();

        Optional<TeacherResponseDTO> teacherOpt = teacherService.getTeacher(cpf);
        if (teacherOpt.isPresent()) {
            TeacherResponseDTO teacher = teacherOpt.get();

            TeacherProjection projection = teacherRepository.findAllByCpf(cpf).get(0);
            if (projection.getPassword() == null || !projection.getPassword().equals(password)) {
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
        return ResponseEntity.ok(new StudentLoginResponseDTO(
                student.getEnrollment(),
                student.getName(),
                student.getEmail(),
                student.getPassword(),
                student.getCpf(),
                firstLogin
        ));
    }
}