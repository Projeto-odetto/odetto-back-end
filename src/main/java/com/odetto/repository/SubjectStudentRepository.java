package com.odetto.repository;

import com.odetto.model.SubjectStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectStudentRepository extends JpaRepository<SubjectStudent, Long> {
    List<SubjectStudent> findByStudentEnrollment(Long enrollment);
}