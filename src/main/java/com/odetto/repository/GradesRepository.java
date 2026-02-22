package com.odetto.repository;

import com.odetto.dto.Grades.StudentGradeResponseDTO;
import com.odetto.model.Grades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface GradesRepository extends JpaRepository<Grades,Long> {
    @Query(value = "select " +
            " com.odetto.dto.Grades.StudentGradeResponseDTO(s2.name, t.name, g.grade,  ROUND((SELECT AVG(elem) FROM unnest(g.grade) AS elem)::numeric, 2) AS media_do_array) from grades g\n" +
            "join report_card rc on g.report_card_id = rc.id \n" +
            "join student s on rc.enrollment_student = s.enrollment \n" +
            "join subjects s2 on s2.id = g.subject_id \n" +
            "join subject_teacher st on st.id_subject = s2.id \n" +
            "join teacher t on t.cpf = st.cpf_teacher \n" +
            "where s.enrollment = :enrollmentStudent", nativeQuery = true)
    List<StudentGradeResponseDTO> findGradesByEnrollmentStudent(Long enrollmentStudent);
}
