package com.odetto.controller;

import com.odetto.dto.Teacher.TeacherCreateRequestDTO;
import com.odetto.dto.Teacher.TeacherRequestDTO;
import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<TeacherRequestDTO> teacherRequests = teacherService.listTeachers();

        List<TeacherResponseDTO> responses = teacherRequests.stream()
                .map(t -> new TeacherResponseDTO(
                        String.valueOf(t.getCpf()),
                        t.getName(),
                        t.getUsername(),
                        t.getHireDate(),
                        t.getSubject()
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }


    @GetMapping("/get-teacher-by-cpf/{cpf}")
    public ResponseEntity<TeacherResponseDTO> getTeacherByCpf(@PathVariable Long cpf) {
        Optional<TeacherRequestDTO> teacherOpt = teacherService.getTeacher(cpf);
        if (teacherOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        TeacherRequestDTO teacher = teacherOpt.get();

        TeacherResponseDTO teacherResponse = new TeacherResponseDTO(
                String.valueOf(teacher.getCpf()),
                teacher.getName(),
                teacher.getUsername(),
                teacher.getHireDate(),
                teacher.getSubject()
        );

        return ResponseEntity.ok(teacherResponse);
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
}
