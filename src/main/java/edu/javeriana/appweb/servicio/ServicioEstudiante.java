package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.EstudianteDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioEstudiante {

    Flux<EstudianteDTO> findAll();

    Mono<EstudianteDTO> findById(Long id);

    Mono<EstudianteDTO> save(EstudianteDTO dto);

    Mono<EstudianteDTO> update(Long id, EstudianteDTO dto);

    Mono<Void> deleteById(Long id);
   Mono<Double> calcularNotaFinal(
        Long estudianteId,
        Long materiaId
);
}