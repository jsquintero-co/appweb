package edu.javeriana.appweb.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("nota")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nota {

    @Id
    private Long id;

    private Double valor;

    private Double porcentaje;

    private String observacion;

    private Long estudianteId;

    private Long materiaId;
}