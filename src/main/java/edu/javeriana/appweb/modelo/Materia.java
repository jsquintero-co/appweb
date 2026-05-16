package edu.javeriana.appweb.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("materia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Materia {

    @Id
    private Long id;

    private String nombre;

    private Integer creditos;
}