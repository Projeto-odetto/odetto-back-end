package com.odetto.repository;

import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.model.Grades;
import com.odetto.projection.StudentGradeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


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
        join report_card rc on g.report_card_id = rc.id
        join student s on rc.enrollment_student = s.enrollment
        join subjects s2 on s2.id = g.subject_id
        join subject_teacher st on st.id_subject = s2.id
        join teacher t on t.cpf = st.cpf_teacher
        where s.enrollment = :enrollmentStudent
        """, nativeQuery = true)
    List<StudentGradeProjection> findGradesByEnrollmentStudent(@Param("enrollmentStudent") Long enrollmentStudent);
}
