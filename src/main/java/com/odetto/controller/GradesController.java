package com.odetto.controller;

import com.odetto.dto.Grades.GradesResponseDTO;
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

    @PatchMapping("/inser-grade/{gradeId}/{grade}")
    public ResponseEntity<String> insertGrade(@PathVariable Long gradeId, @PathVariable Double grade) {
        gradesService.insertGrade(grade, gradeId);
        return ResponseEntity.ok("Grade inserted successfully!");
    }

    @GetMapping("/list-all-grades")
    public ResponseEntity<?> listAllGrades() {
        List<GradesResponseDTO> grades = gradesService.findAllGrades();
        return ResponseEntity.ok(grades);
    }
}
