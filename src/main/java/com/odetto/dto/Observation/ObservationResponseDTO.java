package com.odetto.dto.Observation;

import lombok.Value;

@Value
public class ObservationResponseDTO {
     Long id;
     Long enrollmentStudent;
     Long cpfTeacher;
    String observation;
    Long IdSubject;
}
