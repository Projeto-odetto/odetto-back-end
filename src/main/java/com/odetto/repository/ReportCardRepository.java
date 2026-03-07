package com.odetto.repository;

import com.odetto.model.ReportCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportCardRepository extends JpaRepository<ReportCard, Long> {
    Optional<ReportCard> findByStudentEnrollment(Long enrollment);
}
