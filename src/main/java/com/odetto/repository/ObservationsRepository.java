package com.odetto.repository;

import com.odetto.model.Observations;
import com.odetto.projection.ObservationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObservationsRepository extends JpaRepository<Observations, Long> {
        @Query(value = "select o.* from observations o\n" +
                "where o.id_subject = :subjectId and o.enrollment_student = :enrollment", nativeQuery = true)
    List<Observations> findByStudentEnrollmentAndSubject(Long enrollment, Long subjectId);

        @Query(value = """
        SELECT o.id AS id, t.name AS teacherName, s.name AS subject, o.observation AS observation FROM observations o
        JOIN teacher t ON o.cpf_teacher = t.cpf
        JOIN subject s ON o.id_subject = s.id
        WHERE o.enrollment_student = :enrollment
    """, nativeQuery = true)
    List<ObservationProjection> findByStudentEnrollment(Long enrollment);

    List<Observations> findAllByEnrollmentStudent(Long enrollmentStudent);
}
