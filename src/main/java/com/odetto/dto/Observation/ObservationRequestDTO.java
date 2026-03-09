package com.odetto.dto.Observation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ObservationRequestDTO {
    @NotNull(message = "O campo 'studentName' é obrigatório.")
    private String studentName;
    @NotNull(message = "O campo 'teacherName' é obrigatório.")
    private String teacherName;
    @NotNull(message = "O campo 'subjectName' é obrigatório.")
    private String subjectName;
    @NotNull(message = "O campo 'observation' é obrigatório.")
    private String observation;
}
