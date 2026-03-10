package com.odetto.controller;

import com.odetto.model.ReportCard;
import com.odetto.repository.ReportCardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/report-card")
public class ReportCardController {

    private final ReportCardRepository reportCardRepository;

    public ReportCardController(ReportCardRepository reportCardRepository) {
        this.reportCardRepository = reportCardRepository;
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<ReportCard>> listAll() {
        List<ReportCard> reportCards = reportCardRepository.findAll();
        if (reportCards.isEmpty()) {
            throw new NoSuchElementException("Nenhum boletim encontrado.");
        }
        return ResponseEntity.ok(reportCards);
    }

    @GetMapping("/get-by-enrollment/{enrollment}")
    public ResponseEntity<ReportCard> getByEnrollment(@PathVariable Long enrollment) {
        ReportCard reportCard = reportCardRepository.findByStudentEnrollment(enrollment)
                .orElseThrow(() -> new NoSuchElementException("Boletim não encontrado para a matrícula " + enrollment));
        return ResponseEntity.ok(reportCard);
    }
}