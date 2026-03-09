package com.odetto.service;

import com.odetto.dto.Grades.*;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.model.Grades;
import com.odetto.model.ReportCard;
import com.odetto.model.Student;
import com.odetto.model.Subjects;
import com.odetto.projection.StudentGradeProjection;
import com.odetto.repository.GradesRepository;
import com.odetto.repository.ReportCardRepository;
import com.odetto.repository.StudentRepository;
import com.odetto.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class GradesService {
    private final GradesRepository gradesRepository;
    private final ObjectMapper objectMapper;
    private final ReportCardRepository reportCardRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public GradesService(GradesRepository gradesRepository,
                         ObjectMapper objectMapper,
                         ReportCardRepository reportCardRepository,
                         StudentRepository studentRepository,
                         SubjectRepository subjectRepository) {
        this.gradesRepository = gradesRepository;
        this.objectMapper = objectMapper;
        this.reportCardRepository = reportCardRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<StudentGradeProjection> findGradesByEnrollmentStudent(Long enrollmentId) {
        List<StudentGradeProjection> grades = gradesRepository.findGradesByEnrollmentStudent(enrollmentId);
        if (grades.isEmpty()) {
            throw new NoSuchElementException("Nenhuma nota encontrada para a matrícula do aluno: " + enrollmentId);
        }
        return grades;
    }

    public StudentGradeProjection findGradesByEnrollmentAndSubject(Long enrollment, String subject) {
        Optional<StudentGradeProjection> grades = gradesRepository.findGradesByEnrollmentAndSubject(enrollment, subject);
        if (grades.isPresent()) {
            return grades.get();
        } else {
            throw new NoSuchElementException("Nenhuma nota encontrada para a matrícula do aluno: " + enrollment);
        }
    }

    @Transactional
    public GradeInsertResponseDTO insertGradeByNames(GradeInsertRequestDTO dto) {
        Student student = studentRepository.findByName(dto.getStudentName())
                .orElseThrow(() -> new NoSuchElementException("Estudante não encontrado: " + dto.getStudentName()));

        Subjects subject = subjectRepository.findByName(dto.getSubjectName())
                .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + dto.getSubjectName()));

        ReportCard reportCard = reportCardRepository.findByStudentEnrollment(student.getEnrollment())
                .orElseGet(() -> {
                    ReportCard newReportCard = new ReportCard();
                    newReportCard.setStudentEnrollment(student.getEnrollment());
                    return reportCardRepository.save(newReportCard);
                });

        Grades gradesEntry = gradesRepository.findByReportCardIdAndSubjectId(reportCard.getId(), Long.valueOf(subject.getId()))
                .orElseGet(() -> {
                    Grades newGrades = new Grades();
                    newGrades.setReportCardId(reportCard.getId());
                    newGrades.setSubjectId(Long.valueOf(subject.getId()));
                    newGrades.setGrade(new ArrayList<>());
                    return newGrades;
                });

        List<Double> currentGrades = gradesEntry.getGrade();
        if (currentGrades == null) {
            currentGrades = new ArrayList<>();
        } else {
            currentGrades = new ArrayList<>(currentGrades);
        }

        currentGrades.add(dto.getGradeValue());
        gradesEntry.setGrade(currentGrades);

        gradesRepository.save(gradesEntry);

        return new GradeInsertResponseDTO(
                student.getName(),
                reportCard.getId(),
                subject.getName(),
                dto.getGradeValue()
        );
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

    @Transactional
    public GradeInsertResponseDTO editGradeByNames(GradeEditRequestDTO dto) {
        Student student = studentRepository.findByName(dto.getStudentName())
                .orElseThrow(() -> new NoSuchElementException("Estudante não encontrado: " + dto.getStudentName()));

        Subjects subject = subjectRepository.findByName(dto.getSubjectName())
                .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + dto.getSubjectName()));

        ReportCard reportCard = reportCardRepository.findByStudentEnrollment(student.getEnrollment())
                .orElseThrow(() -> new NoSuchElementException("Boletim não encontrado para o aluno: " + dto.getStudentName()));

        Grades gradesEntry = gradesRepository.findByReportCardIdAndSubjectId(reportCard.getId(), Long.valueOf(subject.getId()))
                .orElseThrow(() -> new NoSuchElementException("Notas não encontradas para a matéria: " + dto.getSubjectName()));

        List<Double> currentGrades = new ArrayList<>(gradesEntry.getGrade());

        if (dto.getGradeIndex() < 0 || dto.getGradeIndex() >= currentGrades.size()) {
            throw new IllegalArgumentException("Índice de nota inválido: " + dto.getGradeIndex());
        }

        currentGrades.set(dto.getGradeIndex(), dto.getNewGradeValue());
        gradesEntry.setGrade(currentGrades);
        gradesRepository.save(gradesEntry);

        return new GradeInsertResponseDTO(
                student.getName(),
                reportCard.getId(),
                subject.getName(),
                dto.getNewGradeValue()
        );
    }
}
