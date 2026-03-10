package com.odetto.dto.Observation;

import lombok.Data;

@Data
public class ObservationEditRequestDTO {
    private Long id;
    private String studentName;
    private String teacherName;
    private String subjectName;
    private String observation;
}