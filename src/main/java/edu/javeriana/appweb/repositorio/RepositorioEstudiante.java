package edu.javeriana.appweb.repositorio;

import edu.javeriana.appweb.modelo.Estudiante;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface RepositorioEstudiante
        extends ReactiveCrudRepository<Estudiante, Long> {

    Mono<Estudiante> findByCorreo(String correo);
}