package com.odetto.service;

import com.odetto.dto.Observation.ObservationEditRequestDTO;
import com.odetto.dto.Observation.ObservationRequestDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.model.Observations;
import com.odetto.model.Student;
import com.odetto.model.Subjects;
import com.odetto.model.Teacher;
import com.odetto.projection.ObservationProjection;
import com.odetto.repository.*;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ObservationService {
    private final ObservationsRepository observationRepository;
    private final ObjectMapper objectMapper;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectTeacherRepository subjectTeacherRepository;

    public ObservationService(ObservationsRepository observationRepository,
                              ObjectMapper objectMapper,
                              StudentRepository studentRepository,
                              TeacherRepository teacherRepository,
                              SubjectRepository subjectRepository,
                              SubjectTeacherRepository subjectTeacherRepository) {
        this.observationRepository = observationRepository;
        this.objectMapper = objectMapper;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.subjectTeacherRepository = subjectTeacherRepository;
    }

    public List<ObservationResponseDTO> listAllObservations() {
        List<Observations> observations = observationRepository.findAll();
        if (observations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma observação encontrada.");
        }
        return observations.stream()
                .map(o -> objectMapper.convertValue(o, ObservationResponseDTO.class))
                .toList();
    }

    public List<ObservationResponseDTO> listObservationsByEnrollmentAndSubject(Long enrollment, Long subjectId) {
        List<Observations> observations = observationRepository.findByStudentEnrollmentAndSubject(enrollment, subjectId);
        if (observations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma observação encontrada para o aluno com matrícula " + enrollment);
        }
        return observations.stream()
                .map(o -> objectMapper.convertValue(o, ObservationResponseDTO.class))
                .toList();
    }

    public List<ObservationProjection> listObservationsByEnrollment2(Long enrollment) {
        List<ObservationProjection> observations = observationRepository.findByStudentEnrollment(enrollment);
        if (observations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma observação encontrada para o aluno com matrícula " + enrollment);
        }
        return observations;
    }

    public ObservationResponseDTO insertObservation(ObservationRequestDTO dto) {
        Student student = studentRepository.findByName(dto.getStudentName())
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado: " + dto.getStudentName()));

        Teacher teacher = teacherRepository.findByName(dto.getTeacherName())
                .orElseThrow(() -> new NoSuchElementException("Professor não encontrado: " + dto.getTeacherName()));

        Subjects subject = subjectRepository.findByName(dto.getSubjectName())
                .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + dto.getSubjectName()));

        boolean teachesSubject = subjectTeacherRepository
                .findByTeacherCpfAndSubjectId(teacher.getCpf(), Long.valueOf(subject.getId()))
                .isPresent();
        if (!teachesSubject) {
            throw new IllegalArgumentException("O professor " + dto.getTeacherName() + " não leciona a matéria " + dto.getSubjectName());
        }

        Observations observation = new Observations();
        observation.setEnrollmentStudent(student.getEnrollment());
        observation.setCpfTeacher(teacher.getCpf());
        observation.setIdSubject(Long.valueOf(subject.getId()));
        observation.setObservation(dto.getObservation());

        Observations saved = observationRepository.save(observation);

        return new ObservationResponseDTO(
                saved.getId(),
                saved.getEnrollmentStudent(),
                saved.getCpfTeacher(),
                saved.getObservation(),
                saved.getIdSubject()
        );
    }

    public ObservationResponseDTO editObservation(ObservationEditRequestDTO dto) {
        Observations observation = observationRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Observação com ID " + dto.getId() + " não encontrada."));

        if (dto.getStudentName() != null && !dto.getStudentName().isBlank()) {
            Student student = studentRepository.findByName(dto.getStudentName())
                    .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado: " + dto.getStudentName()));
            observation.setEnrollmentStudent(student.getEnrollment());
        }

        if (dto.getTeacherName() != null && !dto.getTeacherName().isBlank()) {
            Teacher teacher = teacherRepository.findByName(dto.getTeacherName())
                    .orElseThrow(() -> new NoSuchElementException("Professor não encontrado: " + dto.getTeacherName()));
            observation.setCpfTeacher(teacher.getCpf());
        }

        if (dto.getSubjectName() != null && !dto.getSubjectName().isBlank()) {
            Subjects subject = subjectRepository.findByName(dto.getSubjectName())
                    .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + dto.getSubjectName()));
            observation.setIdSubject(Long.valueOf(subject.getId()));
        }

        boolean teachesSubject = subjectTeacherRepository
                .findByTeacherCpfAndSubjectId(observation.getCpfTeacher(), observation.getIdSubject())
                .isPresent();
        if (!teachesSubject) {
            throw new IllegalArgumentException("O professor não leciona a matéria informada.");
        }

        if (dto.getObservation() != null && !dto.getObservation().isBlank()) {
            observation.setObservation(dto.getObservation());
        }

        Observations saved = observationRepository.save(observation);

        return new ObservationResponseDTO(
                saved.getId(),
                saved.getEnrollmentStudent(),
                saved.getCpfTeacher(),
                saved.getObservation(),
                saved.getIdSubject()
        );
    }

    public void deleteObservation(Long id) {
        observationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Observação com ID " + id + " não encontrada."));
        observationRepository.deleteById(id);
    }
}