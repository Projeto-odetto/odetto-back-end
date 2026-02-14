package com.odetto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Observations {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "enrollment_student")
    private Integer enrollmentStudent;
    @Column(name = "cpf_teacher")
    private Integer cpfTeacher;
    private String observation;
    @Column(name = "ID_subject")
    private Integer IdSubject;
}
