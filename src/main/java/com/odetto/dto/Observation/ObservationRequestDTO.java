package com.odetto.dto.Observation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ObservationRequestDTO {
    @NotNull(message = "O campo 'id' é obrigatório.")
    private Long id;
    @NotNull(message = "O campo 'enrollmentStudent' é obrigatório.")
    private Long enrollmentStudent;
    @NotNull(message = "O campo 'cpfTeacher' é obrigatório.")
    private Long cpfTeacher;
    @NotNull(message = "O campo 'observation' é obrigatório.")
    private String observation;
    @NotNull(message = "O campo 'IdSubject' é obrigatório.")
    private Long idSubject;
}
