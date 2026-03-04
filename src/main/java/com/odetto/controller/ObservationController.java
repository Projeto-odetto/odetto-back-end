package com.odetto.controller;

import com.odetto.dto.Observation.ObservationRequestDTO;
import com.odetto.dto.Observation.ObservationResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.odetto.service.ObservationSevice;

import java.util.List;

@RestController
@RequestMapping("/api/observation")
public class ObservationController {

    private final ObservationSevice observationService;

    public ObservationController(ObservationSevice observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/list-observation-by-enrollment/{enrollment}/{subjectId}")
    public ResponseEntity<List<ObservationResponseDTO>>listObservationByEnrollment(@PathVariable Long enrollment,@PathVariable Long subjectId) {
        List<ObservationResponseDTO> observations = observationService.listObservationsByEnrollment(enrollment,subjectId);
        return ResponseEntity.ok(observations);
    }

    @PostMapping("/insert-observation")
    public ResponseEntity<String> insertObservation(@Valid @RequestBody ObservationRequestDTO observation) {
        observationService.insertObservation(observation);
        return ResponseEntity.ok("Observation inserted successfully!");
    }
}
