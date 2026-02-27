package com.odetto.dto.Observation;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ObservationRequestDTO {
    private Long id;
    private Long enrollmentStudent;
    private Long cpfTeacher;
    private String observation;
    private Long IdSubject;
}
