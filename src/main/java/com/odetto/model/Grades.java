package com.odetto.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_report_card")
    private Long reportCardId;
    @Column(name = "id_subject")
    private Long subjectId;
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "grade", columnDefinition = "float8[]")
    private List<Double> grade;
}