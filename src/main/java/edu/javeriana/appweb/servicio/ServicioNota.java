package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.NotaDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioNota {

    Flux<NotaDTO> findAll();

    Mono<NotaDTO> findById(Long id);

    Flux<NotaDTO> findByEstudianteId(Long estudianteId);

    Mono<NotaDTO> save(NotaDTO dto);

    Mono<NotaDTO> update(Long id, NotaDTO dto);

    Mono<Void> deleteById(Long id);

    Mono<Double> calcularNotaFinal(Long estudianteId);
}