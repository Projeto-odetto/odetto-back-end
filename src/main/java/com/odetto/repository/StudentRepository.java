package com.odetto.repository;

import com.odetto.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "select s.* from student s \n" +
            "join subject_student ss on ss.enrollment_student = s.enrollment \n" +
            "join subjects s2 on s2.id = ss.id_subject \n" +
            "where s2.name = :subjectName", nativeQuery = true)
    List<Student> findStudentsBySubjectName(String subjectName);
}
