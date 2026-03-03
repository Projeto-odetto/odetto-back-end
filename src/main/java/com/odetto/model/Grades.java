package com.odetto.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reportCardId;
    private Long subjectId;
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "grade", columnDefinition = "float8[]")
    private Double[] grade;
}