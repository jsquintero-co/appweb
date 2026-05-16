package edu.javeriana.appweb.repositorio;

import edu.javeriana.appweb.modelo.Materia;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RepositorioMateria
        extends ReactiveCrudRepository<Materia, Long> {

    Mono<Materia> findByNombre(String nombre);
}