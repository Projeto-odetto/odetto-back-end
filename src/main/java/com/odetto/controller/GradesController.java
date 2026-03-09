package com.odetto.controller;

import com.odetto.dto.Grades.*;
import com.odetto.projection.StudentGradeProjection;
import com.odetto.service.GradesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/find-grades-by-enrollment-and-subject/{enrollment}/{subject}")
    public ResponseEntity<?> findGradesByEnrollmentAndSubject(@PathVariable Long enrollment, @PathVariable String subject) {
        StudentGradeProjection grades = gradesService.findGradesByEnrollmentAndSubject(enrollment, subject);
        return ResponseEntity.ok(grades);
    }

    @PostMapping("/insert")
    public ResponseEntity<GradeInsertResponseDTO> insertGrade(@RequestBody GradeInsertRequestDTO dto) {
        GradeInsertResponseDTO response = gradesService.insertGradeByNames(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-all-grades")
    public ResponseEntity<?> listAllGrades() {
        List<GradesResponseDTO> grades = gradesService.findAllGrades();
        return ResponseEntity.ok(grades);
    }

    @PutMapping("/edit")
    public ResponseEntity<GradeInsertResponseDTO> editGrade(@RequestBody GradeEditRequestDTO dto) {
        GradeInsertResponseDTO response = gradesService.editGradeByNames(dto);
        return ResponseEntity.ok(response);
    }
}
