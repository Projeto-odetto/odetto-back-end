package com.odetto.controller;

import com.odetto.dto.Subject.SubjectRequestDTO;
import com.odetto.dto.Subject.SubjectResponseDTO;
import com.odetto.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody SubjectRequestDTO dto) {
        return ResponseEntity.ok(subjectService.createSubject(dto));
    }
}