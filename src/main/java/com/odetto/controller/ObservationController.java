package com.odetto.controller;

import com.odetto.dto.Observation.ObservationResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/list-observation-by-enrollment/{enrollment}/{cpfTeacher}/{subject}")
    public ResponseEntity<List<ObservationResponseDTO>>listObservationByEnrollment(@PathVariable Long enrollment,@PathVariable String subject) {
        List<ObservationResponseDTO> observations = observationService.listObservationsByEnrollment(enrollment,subject);
        return ResponseEntity.ok(observations);
    }
}
