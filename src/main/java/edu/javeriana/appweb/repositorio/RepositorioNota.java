package edu.javeriana.appweb.repositorio;

import edu.javeriana.appweb.modelo.Nota;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RepositorioNota
        extends ReactiveCrudRepository<Nota, Long> {

    Flux<Nota> findByEstudianteId(Long estudianteId);

    Flux<Nota> findByMateriaId(Long materiaId);
    Flux<Nota> findByEstudianteIdAndMateriaId(
        Long estudianteId,
        Long materiaId
);
}