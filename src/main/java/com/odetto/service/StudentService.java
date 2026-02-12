package com.odetto.service;

import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class StudentService {
    private final ObjectMapper objectMapper;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.objectMapper = new ObjectMapper();
    }

    public List<StudentResponseDTO> listStudent() {
        List<StudentResponseDTO> studentResponseDTOS = studentRepository.findAll().stream()
                .map(student -> objectMapper.convertValue(student, StudentResponseDTO.class))
                .toList();
        return studentResponseDTOS;
    }

    public StudentResponseDTO insertStudent(StudentResponseDTO student) {
        var studentEntity = objectMapper.convertValue(student, com.odetto.model.Student.class);
        var savedStudent = studentRepository.save(studentEntity);
        return objectMapper.convertValue(savedStudent, StudentResponseDTO.class);
    }

    public void deleteStudent(Long id) {
            studentRepository.deleteById(id);
    }




}
