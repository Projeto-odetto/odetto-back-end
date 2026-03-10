package com.odetto.repository;

import com.odetto.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LogsRepository extends JpaRepository<Logs, Long> {
    List<Logs> findByDateBetween(LocalDateTime start, LocalDateTime end);
}