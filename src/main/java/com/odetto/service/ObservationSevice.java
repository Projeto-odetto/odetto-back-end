package com.odetto.service;

import com.odetto.dto.Observation.ObservationRequestDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.model.Observations;
import com.odetto.projection.ObservationProjection;
import com.odetto.repository.ObservationsRepository;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ObservationSevice {
    private final ObservationsRepository observationRepository;
    private final ObjectMapper objectMapper;

    public ObservationSevice(ObservationsRepository observationRepository, ObjectMapper objectMapper) {
        this.observationRepository = observationRepository;
        this.objectMapper = objectMapper;
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

    public void insertObservation(ObservationRequestDTO observation) {
        Observations observationEntity = objectMapper.convertValue(observation, Observations.class);
        observationRepository.save(observationEntity);
    }
}
