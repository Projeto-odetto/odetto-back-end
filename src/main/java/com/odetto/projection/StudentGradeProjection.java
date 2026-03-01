package com.odetto.projection;

import java.util.List;

public interface StudentGradeProjection {
    String getSubjectName();
    String getTeacherName();
    List<Double> getGrades();
    Double getAverage();
}
