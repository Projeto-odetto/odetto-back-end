package com.odetto.service;

import com.odetto.dto.LoginRequestDTO;
import com.odetto.dto.Teacher.TeacherCreateRequestDTO;
import com.odetto.dto.Teacher.TeacherRequestDTO;
import com.odetto.dto.Teacher.TeacherResponseDTO;
import com.odetto.model.Observations;
import com.odetto.model.SubjectTeacher;
import com.odetto.model.Subjects;
import com.odetto.model.Teacher;
import com.odetto.projection.TeacherProjection;
import com.odetto.repository.ObservationsRepository;
import com.odetto.repository.SubjectRepository;
import com.odetto.repository.SubjectTeacherRepository;
import com.odetto.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ObjectMapper objectMapper;
    private final SubjectRepository subjectRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;
    private final ObservationsRepository observationsRepository;

    public TeacherService(TeacherRepository teacherRepository,
                          ObjectMapper objectMapper,
                          SubjectRepository subjectRepository,
                          SubjectTeacherRepository subjectTeacherRepository,
                          ObservationsRepository observationsRepository) {
        this.teacherRepository = teacherRepository;
        this.objectMapper = objectMapper;
        this.subjectRepository = subjectRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
        this.observationsRepository = observationsRepository;
    }

    public Optional<TeacherProjection> getTeacherProjection(Long cpf) {
        return teacherRepository.findByCpf(cpf);
    }

    public List<TeacherResponseDTO> listTeachers() {
        return teacherRepository.findAllProjected().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        t -> t.getCpf() + "|" + t.getName() + "|" + t.getUsername() + "|" + t.getHireDate()
                ))
                .entrySet().stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("\\|");
                    List<String> subjects = entry.getValue().stream()
                            .map(TeacherProjection::getSubject)
                            .toList();
                    return new TeacherResponseDTO(parts[0], parts[1], parts[2], parts[3], subjects);
                })
                .toList();
    }

    public Optional<TeacherResponseDTO> getTeacher(Long cpf) {
        List<TeacherProjection> projections = teacherRepository.findAllByCpf(cpf);
        if (projections.isEmpty()) return Optional.empty();

        List<String> subjects = projections.stream()
                .map(TeacherProjection::getSubject)
                .toList();

        TeacherProjection first = projections.get(0);
        return Optional.of(new TeacherResponseDTO(
                first.getCpf(),
                first.getName(),
                first.getUsername(),
                first.getHireDate(),
                subjects
        ));
    }

    public TeacherResponseDTO createTeacher(TeacherCreateRequestDTO dto) {
        Teacher teacher = new Teacher();
        teacher.setCpf(dto.getCpf());
        teacher.setName(dto.getName());
        teacher.setUsername(dto.getUsername());
        teacher.setPassword(dto.getPassword());
        Teacher saved = teacherRepository.save(teacher);

        Teacher savedWithDate = teacherRepository.findById(saved.getCpf())
                .orElseThrow(() -> new NoSuchElementException("Erro ao buscar professor após salvar."));

        if (dto.getSubjectNames() != null && !dto.getSubjectNames().isEmpty()) {
            for (String subjectName : dto.getSubjectNames()) {
                Subjects subject = subjectRepository.findByName(subjectName)
                        .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + subjectName));
                SubjectTeacher link = new SubjectTeacher();
                link.setTeacherCpf(saved.getCpf());
                link.setSubjectId(Long.valueOf(subject.getId()));
                subjectTeacherRepository.save(link);
            }
        }

        return new TeacherResponseDTO(
                String.valueOf(savedWithDate.getCpf()),
                savedWithDate.getName(),
                savedWithDate.getUsername(),
                savedWithDate.getHiredDate() != null ? savedWithDate.getHiredDate().toString() : null,
                dto.getSubjectNames() != null ? dto.getSubjectNames() : List.of()
        );
    }

    @Transactional
    public void deleteTeacher(Long cpf) {
        teacherRepository.findById(cpf)
                .orElseThrow(() -> new NoSuchElementException("Professor com CPF " + cpf + " não encontrado."));

        List<Observations> obs = observationsRepository.findAllByCpfTeacher(cpf);
        observationsRepository.deleteAll(obs);

        List<SubjectTeacher> links = subjectTeacherRepository.findByTeacherCpf(cpf);
        subjectTeacherRepository.deleteAll(links);

        teacherRepository.deleteById(cpf);
    }
}