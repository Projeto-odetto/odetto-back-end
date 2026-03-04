package com.odetto.dto.Grades;

import jakarta.persistence.Column;
import lombok.Value;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Value
public class GradesResponseDTO {
    Long id;
    Long reportCardId;
    Long subjectId;
    List<Double> grade;
}
