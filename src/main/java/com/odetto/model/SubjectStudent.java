package com.odetto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subject_student")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectStudent {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "ID_subject")
    private Long subjectId;
    @Column(name = "enrollment_student")
    private Long studentEnrollment;
}
