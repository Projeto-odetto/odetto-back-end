package com.odetto.service;

import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.model.Teacher;
import com.odetto.repository.StudentRepository;
import com.odetto.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ObjectMapper objectMapper;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        this.objectMapper = new ObjectMapper();
    }

    public TeacherResponseDTO getTeacher(String cpf) {
        Teacher teacher = teacherRepository.findByCpf(cpf);
        return objectMapper.convertValue(teacher, TeacherResponseDTO.class);
    }

    public List<TeacherResponseDTO> listTeachers() {
        List<TeacherResponseDTO> teacherResponseDTOS = teacherRepository.findAll().stream()
                .map(teacher -> objectMapper.convertValue(teacher, TeacherResponseDTO.class))
                .toList();
        return teacherResponseDTOS;
    }

}
