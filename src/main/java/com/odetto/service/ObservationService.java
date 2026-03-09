package com.odetto.service;

import com.odetto.dto.Observation.ObservationRequestDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.model.Observations;
import com.odetto.model.Student;
import com.odetto.model.Subjects;
import com.odetto.model.Teacher;
import com.odetto.projection.ObservationProjection;
import com.odetto.repository.ObservationsRepository;
import com.odetto.repository.StudentRepository;
import com.odetto.repository.SubjectRepository;
import com.odetto.repository.TeacherRepository;
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

    public ObservationService(ObservationsRepository observationRepository,
                              ObjectMapper objectMapper,
                              StudentRepository studentRepository,
                              TeacherRepository teacherRepository,
                              SubjectRepository subjectRepository) {
        this.observationRepository = observationRepository;
        this.objectMapper = objectMapper;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<ObservationResponseDTO> listObservationsByEnrollmentAndSubject(Long enrollment, Long subjectId) {
        List<Observations> observations = observationRepository.findByStudentEnrollmentAndSubject(enrollment, subjectId);
        if (observations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma observação encontrada para o aluno com matrícula " + enrollment);
        }
        return observations.stream()
                .map(observation -> objectMapper.convertValue(observation, ObservationResponseDTO.class))
                .toList();
    }

    public List<ObservationProjection> listObservationsByEnrollment2(Long enrollment) {
        List<ObservationProjection> observations = observationRepository.findByStudentEnrollment(enrollment);
        if (observations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma observação encontrada para o aluno com matrícula " + enrollment);
        }
        return observations.stream()
                .toList();
    }

    public ObservationResponseDTO insertObservation(ObservationRequestDTO dto) {
        Student student = studentRepository.findByName(dto.getStudentName())
                .orElseThrow(() -> new NoSuchElementException("Aluno não encontrado: " + dto.getStudentName()));

        Teacher teacher = teacherRepository.findByName(dto.getTeacherName())
                .orElseThrow(() -> new NoSuchElementException("Professor não encontrado: " + dto.getTeacherName()));

        Subjects subject = subjectRepository.findByName(dto.getSubjectName())
                .orElseThrow(() -> new NoSuchElementException("Matéria não encontrada: " + dto.getSubjectName()));

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
}
