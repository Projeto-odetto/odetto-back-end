package com.odetto.service;

import com.odetto.dto.Observation.ObservationRequestDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.model.Observations;
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

    public List<ObservationResponseDTO> listObservationsByEnrollment(Long enrollment, String subject) {
        List<Observations> observations = observationRepository.FindByStudentEnrollment(enrollment, subject);
        if (observations.isEmpty()) {
            throw new NoSuchElementException("Nenhuma observação encontrada para o aluno com matrícula " + enrollment);
        }
        return observations.stream()
                .map(observation -> objectMapper.convertValue(observation, ObservationResponseDTO.class))
                .toList();
    }

    public void insertObservation(ObservationRequestDTO observation) {
        Observations observationEntity = objectMapper.convertValue(observation, Observations.class);
        observationRespository.save(observationEntity);
    }
}
