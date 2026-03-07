package com.odetto.controller;

import com.odetto.dto.Grades.GradeInsertRequestDTO;
import com.odetto.dto.Grades.GradeInsertResponseDTO;
import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.projection.StudentGradeProjection;
import com.odetto.service.GradesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradesController {
    private final GradesService gradesService;

    public GradesController(GradesService gradesService) {
        this.gradesService = gradesService;
    }

    @GetMapping("/find-grades-by-enrollment/{enrollmentId}")
    public ResponseEntity<?> findGradesByEnrollment(@PathVariable Long enrollmentId) {
        List<StudentGradeProjection> grades = gradesService.findGradesByEnrollmentStudent(enrollmentId);
        return ResponseEntity.ok(grades);
    }

    @PostMapping("/insert")
    public ResponseEntity<GradeInsertResponseDTO> insertGrade(@RequestBody GradeInsertRequestDTO dto) {
        GradeInsertResponseDTO response = gradesService.insertGradeByNames(dto);

        // Retornamos o objeto no corpo da resposta com status 200 (OK)
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-all-grades")
    public ResponseEntity<?> listAllGrades() {
        List<GradesResponseDTO> grades = gradesService.findAllGrades();
        return ResponseEntity.ok(grades);
    }
}
