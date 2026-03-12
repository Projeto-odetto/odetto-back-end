package com.odetto.repository;

import com.odetto.model.SubjectStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectStudentRepository extends JpaRepository<SubjectStudent, Long> {
    List<SubjectStudent> findByStudentEnrollment(Long enrollment);

    List<SubjectStudent> findBySubjectId(Long subjectId);

    Optional<SubjectStudent> findByStudentEnrollmentAndSubjectId(Long enrollment, Long subjectId);
}