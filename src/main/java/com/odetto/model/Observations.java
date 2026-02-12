package com.odetto.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnrollmentStudent() {
        return enrollmentStudent;
    }

    public void setEnrollmentStudent(Integer enrollmentStudent) {
        this.enrollmentStudent = enrollmentStudent;
    }

    public Integer getCpfTeacher() {
        return cpfTeacher;
    }

    public void setCpfTeacher(Integer cpfTeacher) {
        this.cpfTeacher = cpfTeacher;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getIdSubject() {
        return IdSubject;
    }



    public void setIdSubject(Integer idSubject) {
        IdSubject = idSubject;
    }
}
