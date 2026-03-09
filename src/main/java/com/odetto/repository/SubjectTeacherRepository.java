package com.odetto.repository;

import com.odetto.model.SubjectTeacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectTeacherRepository extends JpaRepository<SubjectTeacher, Long> {
    List<SubjectTeacher> findByTeacherCpf(Long teacherCpf);
}