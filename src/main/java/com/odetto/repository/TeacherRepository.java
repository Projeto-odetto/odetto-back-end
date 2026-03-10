package com.odetto.repository;

import com.odetto.model.Teacher;
import com.odetto.projection.TeacherProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = """
        SELECT t.cpf AS cpf, t.password AS password, t.name AS name, t.username AS username, t.hired_date AS hireDate, s.name AS subject FROM subject_teacher st
        JOIN teacher t ON st.cpf_teacher = t.cpf
        JOIN subject s ON st.id_subject = s.id
        WHERE t.cpf = :cpf
    """, nativeQuery = true)
    Optional<TeacherProjection> findByCpf(Long cpf);

    @Query(value = """
        SELECT t.cpf AS cpf, t.password AS password, t.name AS name, t.username AS username, t.hired_date AS hireDate, s.name AS subject FROM subject_teacher st
        JOIN teacher t ON st.cpf_teacher = t.cpf
        JOIN subject s ON st.id_subject = s.id
    """, nativeQuery = true)
    List<TeacherProjection> findAllProjected();

    Optional<Teacher> findByName(String name);

    @Query(value = """
    SELECT t.cpf AS cpf, t.password AS password, t.name AS name, t.username AS username, t.hired_date AS hireDate, s.name AS subject FROM subject_teacher st
    JOIN teacher t ON st.cpf_teacher = t.cpf
    JOIN subject s ON st.id_subject = s.id
    WHERE t.cpf = :cpf
""", nativeQuery = true)
    List<TeacherProjection> findAllByCpf(Long cpf);
}