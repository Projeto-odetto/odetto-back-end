package com.odetto.controller;

import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.projection.StudentGradeProjection;
import com.odetto.service.GradesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
