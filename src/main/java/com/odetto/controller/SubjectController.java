package com.odetto.controller;

import com.odetto.dto.Subject.SubjectEditRequestDTO;
import com.odetto.dto.Subject.SubjectRequestDTO;
import com.odetto.dto.Subject.SubjectResponseDTO;
import com.odetto.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<SubjectResponseDTO>> listAll() {
        return ResponseEntity.ok(subjectService.listAllSubjects());
    }

    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<SubjectResponseDTO> getByName(@PathVariable String name) {
        return ResponseEntity.ok(subjectService.getSubjectByName(name));
    }

    @PostMapping("/create")
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody SubjectRequestDTO dto) {
        return ResponseEntity.ok(subjectService.createSubject(dto));
    }

    @PatchMapping("/edit")
    public ResponseEntity<SubjectResponseDTO> editSubject(@RequestBody SubjectEditRequestDTO dto) {
        return ResponseEntity.ok(subjectService.editSubject(dto.getId(), dto.getName()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Integer id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Matéria deletada com sucesso.");
    }
}