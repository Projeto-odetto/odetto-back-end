package com.odetto.controller;

import com.odetto.dto.Observation.ObservationRequestDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import com.odetto.projection.ObservationProjection;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.odetto.service.ObservationService;

import java.util.List;

@RestController
@RequestMapping("/api/observation")
public class ObservationController {

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/list-observation-by-enrollment/{enrollment}/{subjectId}")
    public ResponseEntity<List<ObservationResponseDTO>>listObservationByEnrollmentAndSubject(@PathVariable Long enrollment,@PathVariable Long subjectId) {
        List<ObservationResponseDTO> observations = observationService.listObservationsByEnrollmentAndSubject(enrollment,subjectId);
        return ResponseEntity.ok(observations);
    }

    @GetMapping("/list-observation-by-enrollment/{enrollment}")
    public ResponseEntity<List<ObservationProjection>>listObservationByEnrollment2(@PathVariable Long enrollment) {
        List<ObservationProjection> observations = observationService.listObservationsByEnrollment2(enrollment);
        return ResponseEntity.ok(observations);
    }

    @PostMapping("/insert-observation")
    public ResponseEntity<ObservationResponseDTO> insertObservation(@Valid @RequestBody ObservationRequestDTO observation) {
        ObservationResponseDTO response = observationService.insertObservation(observation);
        return ResponseEntity.ok(response);
    }
}
