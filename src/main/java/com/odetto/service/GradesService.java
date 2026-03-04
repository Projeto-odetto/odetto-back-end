package com.odetto.service;

import com.odetto.dto.Grades.GradesResponseDTO;
import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.model.Grades;
import com.odetto.projection.StudentGradeProjection;
import com.odetto.repository.GradesRepository;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void insertGrade(Double grade, Long gradeId) {
        Grades grades = gradesRepository.findById(gradeId).get();
        grades.getGrade().add(grade);
        gradesRepository.save(grades);
    }

    public List<GradesResponseDTO> findAllGrades() {
        List<Grades> grades = gradesRepository.findAll();
        if (grades.isEmpty()) {
            throw new NoSuchElementException("Nenhuma nota encontrada.");
        }
        return grades.stream()
                .map(grade -> objectMapper.convertValue(grade, GradesResponseDTO.class))
                .toList();
    }
}
