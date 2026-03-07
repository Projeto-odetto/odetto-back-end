package com.odetto.service;

import com.odetto.dto.Grades.GradeInsertRequestDTO;
import com.odetto.dto.Grades.GradeInsertResponseDTO;
import com.odetto.dto.Grades.GradesResponseDTO;
import com.odetto.dto.Grades.StudentGradeResponseDTO;
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

    @Transactional
    public GradeInsertResponseDTO insertGradeByNames(GradeInsertRequestDTO dto) {
        // 1. Busca Aluno pelo Nome
        Student student = studentRepository.findByName(dto.getStudentName())
                .orElseThrow(() -> new NoSuchElementException("Estudante não encontrado: " + dto.getStudentName()));

        // 2. Busca Matéria pelo Nome
        Subjects subject = subjectRepository.findByName(dto.getSubjectName())
                .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + dto.getSubjectName()));

        // 3. Busca o Boletim do Aluno (ou cria um novo se não existir)
        ReportCard reportCard = reportCardRepository.findByStudentEnrollment(student.getEnrollment())
                .orElseGet(() -> {
                    ReportCard newReportCard = new ReportCard();
                    // Aqui você define a matrícula do aluno no novo boletim
                    // O nome do metodo set pode variar dependendo do seu modelo ReportCard
                    newReportCard.setStudentEnrollment(student.getEnrollment());
                    return reportCardRepository.save(newReportCard);
                });

        // 4. Busca se já existe registro de notas para essa matéria neste boletim
        Grades gradesEntry = gradesRepository.findByReportCardIdAndSubjectId(reportCard.getId(), Long.valueOf(subject.getId()))
                .orElseGet(() -> {
                    // Se não existir, cria um novo objeto Grades
                    Grades newGrades = new Grades();
                    newGrades.setReportCardId(reportCard.getId());
                    newGrades.setSubjectId(Long.valueOf(subject.getId()));
                    newGrades.setGrade(new ArrayList<>());
                    return newGrades;
                });

        // 5. Faz o "append" da nota (Garantindo que a lista seja mutável)
        List<Double> currentGrades = gradesEntry.getGrade();
        if (currentGrades == null) {
            currentGrades = new ArrayList<>();
        } else {
            currentGrades = new ArrayList<>(currentGrades); // Evita problemas com coleções imutáveis
        }

        currentGrades.add(dto.getGradeValue());
        gradesEntry.setGrade(currentGrades);

        // 6. Salva (Insert ou Update automático pelo JPA)
        gradesRepository.save(gradesEntry);

        return new GradeInsertResponseDTO(
                student.getName(),
                reportCard.getId(),
                subject.getName(),
                dto.getGradeValue() // a variável da nota que chegou no parâmetro
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
}
