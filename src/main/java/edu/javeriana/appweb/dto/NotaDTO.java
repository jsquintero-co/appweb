package edu.javeriana.appweb.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NotaDTO {

    private Long id;

    @NotNull(message = "La nota es obligatoria")
    @Min(value = 0, message = "La nota mínima es 0")
    @Max(value = 5, message = "La nota máxima es 5")
    private Double valor;

    @NotNull(message = "El porcentaje es obligatorio")
    @Min(value = 0, message = "El porcentaje mínimo es 0")
    @Max(value = 100, message = "El porcentaje máximo es 100")
    private Double porcentaje;

    private String observacion;

    private Long estudianteId;

    private Long materiaId;

    private String materiaNombre;
}