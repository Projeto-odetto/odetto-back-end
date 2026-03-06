package com.odetto.service;

import com.odetto.dto.LoginRequestDTO;
import com.odetto.dto.Teacher.TeacherRequestDTO;
import com.odetto.dto.Teacher.TeacherResponseDTO;
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

    public Optional<TeacherRequestDTO> getTeacher(Long cpf) {
        return teacherRepository.findByCpf(cpf)
                .map(teacher -> objectMapper.convertValue(teacher, TeacherRequestDTO.class));
    }

    public List<TeacherRequestDTO> listTeachers() {
        return teacherRepository.findAllProjected().stream()
                .map(teacher -> objectMapper.convertValue(teacher, TeacherRequestDTO.class))
                .toList();
    }
}