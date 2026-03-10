package com.odetto.service;

import com.odetto.dto.Subject.SubjectRequestDTO;
import com.odetto.dto.Subject.SubjectResponseDTO;
import com.odetto.model.Subjects;
import com.odetto.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ObjectMapper objectMapper;
    private final GradesRepository gradesRepository;
    private final ObservationsRepository observationsRepository;
    private final SubjectStudentRepository subjectStudentRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          ObjectMapper objectMapper,
                          GradesRepository gradesRepository,
                          ObservationsRepository observationsRepository,
                          SubjectStudentRepository subjectStudentRepository,
                          SubjectTeacherRepository subjectTeacherRepository) {
        this.subjectRepository = subjectRepository;
        this.objectMapper = objectMapper;
        this.gradesRepository = gradesRepository;
        this.observationsRepository = observationsRepository;
        this.subjectStudentRepository = subjectStudentRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
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

    @Transactional
    public void deleteSubject(Integer id) {
        Subjects subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria com ID " + id + " não encontrada."));

        gradesRepository.deleteAll(gradesRepository.findBySubjectId(Long.valueOf(id)));
        observationsRepository.deleteAll(observationsRepository.findAllByIdSubject(Long.valueOf(id)));
        subjectStudentRepository.deleteAll(subjectStudentRepository.findBySubjectId(Long.valueOf(id)));
        subjectTeacherRepository.deleteAll(subjectTeacherRepository.findBySubjectId(Long.valueOf(id)));

        subjectRepository.delete(subject);
    }

    public List<SubjectResponseDTO> listAllSubjects() {
        List<Subjects> subjects = subjectRepository.findAll();
        if (subjects.isEmpty()) {
            throw new NoSuchElementException("Nenhuma matéria encontrada.");
        }
        return subjects.stream()
                .map(s -> objectMapper.convertValue(s, SubjectResponseDTO.class))
                .toList();
    }

    public SubjectResponseDTO getSubjectByName(String name) {
        Subjects subject = subjectRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + name));
        return objectMapper.convertValue(subject, SubjectResponseDTO.class);
    }

    public SubjectResponseDTO editSubject(Integer id, String name) {
        Subjects subject = subjectRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Matéria com ID " + id + " não encontrada."));

        if (subjectRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("Já existe uma matéria com o nome: " + name);
        }

        subject.setName(name);
        Subjects saved = subjectRepository.save(subject);
        return objectMapper.convertValue(saved, SubjectResponseDTO.class);
    }
}