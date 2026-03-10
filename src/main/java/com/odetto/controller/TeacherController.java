package com.odetto.controller;

import com.odetto.dto.Teacher.TeacherCreateRequestDTO;
import com.odetto.dto.Teacher.TeacherEditRequestDTO;
import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.dto.Teacher.TeacherSubjectEditRequestDTO;
import com.odetto.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/list-teachers")
    public ResponseEntity<List<TeacherResponseDTO>> listTeachers() {
        return ResponseEntity.ok(teacherService.listTeachers());
    }

    @GetMapping("/get-teacher-by-cpf/{cpf}")
    public ResponseEntity<TeacherResponseDTO> getTeacherByCpf(@PathVariable Long cpf) {
        return teacherService.getTeacher(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @PostMapping("/create")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody TeacherCreateRequestDTO dto) {
        return ResponseEntity.ok(teacherService.createTeacher(dto));
    }

    @DeleteMapping("/delete/{cpf}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long cpf) {
        teacherService.deleteTeacher(cpf);
        return ResponseEntity.ok("Professor deletado com sucesso.");
    }

    @PatchMapping("/edit")
    public ResponseEntity<TeacherResponseDTO> editTeacher(@RequestBody TeacherEditRequestDTO dto) {
        return ResponseEntity.ok(teacherService.editTeacher(dto));
    }

    @PatchMapping("/edit-subjects")
    public ResponseEntity<TeacherResponseDTO> editTeacherSubjects(@RequestBody TeacherSubjectEditRequestDTO dto) {
        return ResponseEntity.ok(teacherService.editTeacherSubjects(dto));
    }
}