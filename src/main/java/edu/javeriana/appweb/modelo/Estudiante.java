package edu.javeriana.appweb.modelo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("estudiante")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {

    @Id
    private Long id;

    private String nombre;

    private String apellido;

    private String correo;
}