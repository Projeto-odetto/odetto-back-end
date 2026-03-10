package com.odetto.controller;

import com.odetto.dto.Observation.ObservationEditRequestDTO;
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

    @GetMapping("/list-all")
    public ResponseEntity<List<ObservationResponseDTO>> listAllObservations() {
        return ResponseEntity.ok(observationService.listAllObservations());
    }

    @GetMapping("/list-by-enrollment-and-subject/{enrollment}/{subjectId}")
    public ResponseEntity<List<ObservationResponseDTO>> listByEnrollmentAndSubject(
            @PathVariable Long enrollment, @PathVariable Long subjectId) {
        return ResponseEntity.ok(observationService.listObservationsByEnrollmentAndSubject(enrollment, subjectId));
    }

    @GetMapping("/list-by-enrollment/{enrollment}")
    public ResponseEntity<List<ObservationProjection>> listByEnrollment(@PathVariable Long enrollment) {
        return ResponseEntity.ok(observationService.listObservationsByEnrollment2(enrollment));
    }

    @PostMapping("/insert")
    public ResponseEntity<ObservationResponseDTO> insertObservation(@Valid @RequestBody ObservationRequestDTO dto) {
        return ResponseEntity.ok(observationService.insertObservation(dto));
    }

    @PatchMapping("/edit")
    public ResponseEntity<ObservationResponseDTO> editObservation(@RequestBody ObservationEditRequestDTO dto) {
        return ResponseEntity.ok(observationService.editObservation(dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObservation(@PathVariable Long id) {
        observationService.deleteObservation(id);
        return ResponseEntity.ok("Observação deletada com sucesso.");
    }
}