package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.EstudianteDTO;
import edu.javeriana.appweb.dto.MateriaDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServicioMateria {

    Flux<MateriaDTO> findAll();

    Mono<MateriaDTO> findById(Long id);

    Mono<MateriaDTO> save(MateriaDTO dto);

    Mono<MateriaDTO> update(Long id, MateriaDTO dto);

    Mono<Void> deleteById(Long id);

    Flux<Long> obtenerIdsEstudiantesPorMateria(Long materiaId);

}