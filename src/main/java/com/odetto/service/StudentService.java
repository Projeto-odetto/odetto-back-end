package com.odetto.service;

import com.odetto.dto.Student.StudentEditRequestDTO;
import com.odetto.dto.admin.PreCadastroRequestDTO;
import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.dto.Student.StudentFinalCadastroDTO;
import com.odetto.model.Grades;
import com.odetto.model.Observations;
import com.odetto.model.Student;
import com.odetto.model.SubjectStudent;
import com.odetto.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.odetto.util.EnrollmentGenerator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {
    private final ObjectMapper objectMapper;
    private final StudentRepository studentRepository;
    private final EmailService emailService;
    private final ReportCardRepository reportCardRepository;
    private final GradesRepository gradesRepository;
    private final ObservationsRepository observationsRepository;
    private final SubjectStudentRepository subjectStudentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          ObjectMapper objectMapper,
                          EmailService emailService,
                          ReportCardRepository reportCardRepository,
                          GradesRepository gradesRepository,
                          ObservationsRepository observationsRepository,
                          SubjectStudentRepository subjectStudentRepository) {
        this.studentRepository = studentRepository;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.reportCardRepository = reportCardRepository;
        this.gradesRepository = gradesRepository;
        this.observationsRepository = observationsRepository;
        this.subjectStudentRepository = subjectStudentRepository;
    }

    public List<StudentResponseDTO> listStudent() {
        return studentRepository.findAll().stream()
                .map(student -> objectMapper.convertValue(student, StudentResponseDTO.class))
                .toList();
    }

    public List<StudentResponseDTO> findStudentsBySubjectName(String subjectName) {
        List<StudentResponseDTO> studentResponseDTOS = studentRepository.findStudentsBySubjectName(subjectName).stream()
                .map(student -> objectMapper.convertValue(student, StudentResponseDTO.class))
                .toList();
        if (studentResponseDTOS.isEmpty()) {
            throw new NoSuchElementException("Nenhum estudante encontrado para a disciplina: " + subjectName);
        }
        return studentResponseDTOS;
    }

    public StudentResponseDTO preCadastro(PreCadastroRequestDTO dto) {
        Student student = new Student();
        student.setCpf(dto.getCpf());
        student.setEmail(dto.getEmail());
        student.setName(null);

        Long newEnrollment = EnrollmentGenerator.generate();

        while (studentRepository.existsById(newEnrollment)) {
            newEnrollment = EnrollmentGenerator.generate();
        }

        student.setEnrollment(newEnrollment);
        student.setPassword(String.valueOf(newEnrollment));

        Student saved = studentRepository.save(student);

        try {
            emailService.sendPreCadastroEmail(saved);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }

        return objectMapper.convertValue(saved, StudentResponseDTO.class);
    }

    public Optional<Student> findStudentByCpf(Long cpf) {
        return studentRepository.findByCpf(cpf);
    }

    public StudentResponseDTO finalCadastro(StudentFinalCadastroDTO dto) {
        Student student = studentRepository.findById(dto.getEnrollment())
                .orElseThrow(() -> new NoSuchElementException("Estudante com matrícula " + dto.getEnrollment() + " não encontrado."));

        if (student.getName() != null && !student.getName().isEmpty()) {
            throw new IllegalArgumentException("Este aluno já realizou o cadastro final.");
        }

        student.setName(dto.getName());
        student.setPassword(dto.getPassword());

        Student saved = studentRepository.save(student);

        return objectMapper.convertValue(saved, StudentResponseDTO.class);
    }

    @Transactional
    public void deleteStudent(Long enrollment) {
        Student student = studentRepository.findById(enrollment)
                .orElseThrow(() -> new NoSuchElementException("Estudante com matrícula " + enrollment + " não encontrado."));

        reportCardRepository.findByStudentEnrollment(enrollment).ifPresent(rc -> {
            List<Grades> grades = gradesRepository.findByReportCardId(rc.getId());
            gradesRepository.deleteAll(grades);
            reportCardRepository.delete(rc);
        });

        List<Observations> obs = observationsRepository.findAllByEnrollmentStudent(enrollment);
        observationsRepository.deleteAll(obs);

        List<SubjectStudent> links = subjectStudentRepository.findByStudentEnrollment(enrollment);
        subjectStudentRepository.deleteAll(links);

        studentRepository.delete(student);
    }

    public StudentResponseDTO editStudent(StudentEditRequestDTO dto) {
        Student student = studentRepository.findById(dto.getEnrollment())
                .orElseThrow(() -> new NoSuchElementException("Estudante com matrícula " + dto.getEnrollment() + " não encontrado."));

        if (dto.getName() != null && !dto.getName().isBlank()) {
            student.setName(dto.getName());
        }
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            student.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            student.setPassword(dto.getPassword());
        }

        Student saved = studentRepository.save(student);
        return objectMapper.convertValue(saved, StudentResponseDTO.class);
    }
}