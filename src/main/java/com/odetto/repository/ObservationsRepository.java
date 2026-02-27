package com.odetto.repository;

import com.odetto.model.Observations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObservationsRepository extends JpaRepository<Observations, Long> {
    @Query(value = "select o.* from observations o\n" +
            "join student s on s.enrollment = o.enrollment_student\n" +
            "join teacher t on t.cpf = o.cpf_teacher \n" +
            "join subjects s2 on s2.id = o.id_subject \n" +
            "where s2.name = :subject and s.enrollment = :enrollment" , nativeQuery = true)
    List<Observations> FindByStudentEnrollment(Long enrollment, String subject);

}
