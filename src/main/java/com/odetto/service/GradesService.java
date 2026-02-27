package com.odetto.service;

import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.projection.StudentGradeProjection;
import com.odetto.repository.GradesRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class GradesService {
    private final GradesRepository gradesRepository;
    private final ObjectMapper objectMapper;

    public GradesService(GradesRepository gradesRepository, ObjectMapper objectMapper) {
        this.gradesRepository = gradesRepository;
        this.objectMapper = objectMapper;
    }

    public List<StudentGradeProjection> findGradesByEnrollmentStudent(Long enrollmentId) {
        List<StudentGradeProjection> grades = gradesRepository.findGradesByEnrollmentStudent(enrollmentId);
        if (grades.isEmpty()) {
            throw new NoSuchElementException("Nenhuma nota encontrada para a matrícula do aluno: " + enrollmentId);
        }
        return grades;
    }
}
