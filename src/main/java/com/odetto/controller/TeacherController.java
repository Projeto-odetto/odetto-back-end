package com.odetto.controller;

import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/list-teachers")
    public ResponseEntity<List<TeacherResponseDTO>> listTeachers() {
        List<TeacherResponseDTO> teachers = teacherService.listTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/get-teacher-by-cpf/{cpf}")
    public ResponseEntity<TeacherResponseDTO> getTeacherByCpf(@PathVariable Long cpf) {
        Optional<TeacherResponseDTO> teacherOpt = teacherService.getTeacher(cpf);
        if (teacherOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null); // ou mensagem custom
        }
        return ResponseEntity.ok(teacherOpt.get());
    }


}
