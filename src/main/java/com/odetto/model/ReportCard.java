package com.odetto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Report_Card")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCard {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "enrollment_student")
    private Long studentEnrollment;
}
