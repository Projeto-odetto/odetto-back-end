package com.odetto.repository;

import com.odetto.model.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher, Long> {
    List<SubjectTeacher> findByTeacherCpf(Long teacherCpf);

    List<SubjectTeacher> findBySubjectId(Long subjectId);

    Optional<SubjectTeacher> findByTeacherCpfAndSubjectId(Long teacherCpf, Long subjectId);
}