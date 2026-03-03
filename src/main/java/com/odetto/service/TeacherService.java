package com.odetto.service;

import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.model.Teacher;
import com.odetto.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ObjectMapper objectMapper;

    public TeacherService(TeacherRepository teacherRepository, ObjectMapper objectMapper) {
        this.teacherRepository = teacherRepository;
        this.objectMapper = objectMapper;
    }

    public Optional<TeacherResponseDTO> getTeacher(Long cpf) {
        return teacherRepository.findByCpf(cpf)
                .map(teacher -> objectMapper.convertValue(teacher, TeacherResponseDTO.class));
    }

    public List<TeacherResponseDTO> listTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacher -> objectMapper.convertValue(teacher, TeacherResponseDTO.class))
                .toList();
    }
}