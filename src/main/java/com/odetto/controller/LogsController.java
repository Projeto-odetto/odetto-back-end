package com.odetto.controller;

import com.odetto.model.Logs;
import com.odetto.repository.LogsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/logs")
public class LogsController {

    private final LogsRepository logsRepository;

    public LogsController(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @GetMapping("/list-all")
    public ResponseEntity<List<Logs>> listAll() {
        List<Logs> logs = logsRepository.findAll();
        if (logs.isEmpty()) {
            throw new NoSuchElementException("Nenhum log encontrado.");
        }
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/get-by-date/{date}")
    public ResponseEntity<List<Logs>> getByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Logs> logs = logsRepository.findByDateBetween(
                localDate.atStartOfDay(),
                localDate.plusDays(1).atStartOfDay()
        );
        if (logs.isEmpty()) {
            throw new NoSuchElementException("Nenhum log encontrado para a data: " + date);
        }
        return ResponseEntity.ok(logs);
    }
}