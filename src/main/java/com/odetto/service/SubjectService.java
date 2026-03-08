package com.odetto.service;

import com.odetto.dto.Subject.SubjectRequestDTO;
import com.odetto.dto.Subject.SubjectResponseDTO;
import com.odetto.model.Subjects;
import com.odetto.repository.SubjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ObjectMapper objectMapper;

    public SubjectService(SubjectRepository subjectRepository, ObjectMapper objectMapper) {
        this.subjectRepository = subjectRepository;
        this.objectMapper = objectMapper;
    }

    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {
        if (subjectRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Matéria já cadastrada: " + dto.getName());
        }
        Subjects subject = new Subjects();
        subject.setName(dto.getName());
        Subjects saved = subjectRepository.save(subject);
        return objectMapper.convertValue(saved, SubjectResponseDTO.class);
    }
}