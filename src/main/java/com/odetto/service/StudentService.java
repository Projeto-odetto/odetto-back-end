package com.odetto.service;

import com.odetto.dto.Admin.PreCadastroRequestDTO;
import com.odetto.dto.Student.StudentRequestDTO;
import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.model.Student;
import com.odetto.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return studentRepository.findStudentsBySubjectName(subjectName).stream()
                .map(student -> objectMapper.convertValue(student, StudentResponseDTO.class))
                .toList();
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public StudentResponseDTO preCadastro(PreCadastroRequestDTO dto) {
        Student student = new Student();
        student.setName(null);
        student.setEmail(dto.getEmail());
        student.setCpf(dto.getCpf());
        student.setPassword(null);

        Student saved = studentRepository.save(student);

        String generatedPassword = String.valueOf(saved.getEnrollment());
        saved.setPassword(generatedPassword);
        Student updated = studentRepository.save(saved);

        emailService.sendPreCadastroEmail(updated);

        return objectMapper.convertValue(updated, StudentResponseDTO.class);
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
}