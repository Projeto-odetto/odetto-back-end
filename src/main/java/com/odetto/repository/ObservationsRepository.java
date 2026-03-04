package com.odetto.repository;

import com.odetto.model.Observations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObservationsRepository extends JpaRepository<Observations, Long> {
        @Query(value = "select o.* from observations o\n" +
                "where o.id_subject = :subjectId and o.enrollment_student = :enrollment", nativeQuery = true)
    List<Observations> FindByStudentEnrollment(Long enrollment, Long subjectId);

}
