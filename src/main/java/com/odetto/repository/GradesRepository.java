package com.odetto.repository;

import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.model.Grades;
import com.odetto.projection.StudentGradeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface GradesRepository extends JpaRepository<Grades,Long> {
    @Query(value = """
        select 
            s2.name as subjectName,
            t.name as teacherName,
            g.grade as grades,
            ROUND(CAST(
                (SELECT AVG(elem) FROM unnest(g.grade) elem)
            AS numeric), 2) as average
        from grades g
        join report_card rc on g.id_report_card = rc.id\s
        join student s on rc.enrollment_student = s.enrollment\s
        join subject s2 on s2.id = g.id_subject\s
        join subject_teacher st on st.id_subject = s2.id\s
        join teacher t on t.cpf = st.cpf_teacher\s
        where s.enrollment = :enrollmentStudent
        """, nativeQuery = true)
    List<StudentGradeProjection> findGradesByEnrollmentStudent(@Param("enrollmentStudent") Long enrollmentStudent);

    Optional<Grades> findByReportCardIdAndSubjectId(Long reportCardId, Long subjectId);

    @Query(value = """
        select
            s2.name as subjectName,
            t.name as teacherName,
            g.grade as grades,
            ROUND(CAST(
                          (SELECT AVG(elem) FROM unnest(g.grade) elem)
                      AS numeric), 2) as average
        from grades g
                 join report_card rc on g.id_report_card = rc.id
        join student s on rc.enrollment_student = s.enrollment
        join subject s2 on s2.id = g.id_subject
        join subject_teacher st on st.id_subject = s2.id
        join teacher t on t.cpf = st.cpf_teacher
        where s.enrollment = :enrollment
        and s2.name = :subject
        limit 1
    """, nativeQuery = true)
    Optional<StudentGradeProjection> findGradesByEnrollmentAndSubject(Long enrollment, String subject);

    List<Grades> findByReportCardId(Long reportCardId);
}
