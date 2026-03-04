package com.odetto.service;

import com.odetto.dto.admin.PreCadastroRequestDTO;
import com.odetto.dto.Student.StudentRequestDTO;
import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.model.Student;
import com.odetto.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.odetto.util.EnrollmentGenerator;
import com.odetto.dto.Student.StudentFinalCadastroDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentService {
    private final ObjectMapper objectMapper;
    private final StudentRepository studentRepository;
    private final EmailService emailService;

    @Autowired
    public StudentService(StudentRepository studentRepository, ObjectMapper objectMapper, EmailService emailService) {
        this.studentRepository = studentRepository;
        this.objectMapper = objectMapper;
        this.emailService = emailService;
    }

    public List<StudentResponseDTO> listStudent() {
        return studentRepository.findAll().stream()
                .map(student -> objectMapper.convertValue(student, StudentResponseDTO.class))
                .toList();
    }

    public StudentResponseDTO insertStudent(StudentRequestDTO student) {
        Student studentEntity = objectMapper.convertValue(student, Student.class);
        Student savedStudent = studentRepository.save(studentEntity);
        return objectMapper.convertValue(savedStudent, StudentResponseDTO.class);
    }

    public List<StudentResponseDTO> findStudentsBySubjectName(String subjectName) {
        List<StudentResponseDTO> studentResponseDTOS =  studentRepository.findStudentsBySubjectName(subjectName).stream()
                .map(student -> objectMapper.convertValue(student, StudentResponseDTO.class))
                .toList();
        if (studentResponseDTOS.isEmpty()) {
            throw new NoSuchElementException("Nenhum estudante encontrado para a disciplina: " + subjectName);
        }
        return studentResponseDTOS;
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
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

    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO student) {
        Student studentEntity = objectMapper.convertValue(student, Student.class);
        studentEntity.setEnrollment(id);
        Student saved = studentRepository.save(studentEntity);
        return objectMapper.convertValue(saved, StudentResponseDTO.class);
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
}