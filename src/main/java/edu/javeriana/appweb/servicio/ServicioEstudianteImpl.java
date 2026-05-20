package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.EstudianteDTO;
import edu.javeriana.appweb.modelo.Estudiante;
import edu.javeriana.appweb.repositorio.RepositorioEstudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServicioEstudianteImpl
        implements ServicioEstudiante {

    @Autowired
    private RepositorioEstudiante repositorio;

    @Autowired
    private ServicioNota servicioNota;

    @Override
    public Flux<EstudianteDTO> findAll() {

        return repositorio.findAll()

                .limitRate(10)

                .map(this::convertToDTO);
    }

    @Override
    public Mono<EstudianteDTO> findById(Long id) {

        return repositorio.findById(id)

                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException(
                                        "Estudiante no encontrado"
                                )
                        )
                )

                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Mono<EstudianteDTO> save(EstudianteDTO dto) {

        return repositorio.findByCorreo(dto.getCorreo())

                .flatMap(existente ->

                        Mono.error(
                                new RuntimeException(
                                        "El correo ya existe"
                                )
                        )
                )

                .switchIfEmpty(

                        repositorio.save(
                                convertToEntity(dto)
                        )
                )

                .map(estudiante -> (Estudiante) estudiante)

                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Mono<EstudianteDTO> update(
            Long id,
            EstudianteDTO dto
    ) {

        return repositorio.findById(id)

                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException(
                                        "Estudiante no encontrado"
                                )
                        )
                )

                .flatMap(estudiante -> {

                    estudiante.setNombre(dto.getNombre());

                    estudiante.setApellido(dto.getApellido());

                    estudiante.setCorreo(dto.getCorreo());

                    return repositorio.save(estudiante);
                })

                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(Long id) {

        return repositorio.deleteById(id);
    }

    @Override
    public Mono<Double> calcularNotaFinal(
            Long estudianteId,
            Long materiaId
    ) {

        return servicioNota.calcularNotaFinal(
                estudianteId,
                materiaId
        );
    }

    private EstudianteDTO convertToDTO(
            Estudiante estudiante
    ) {

        EstudianteDTO dto = new EstudianteDTO();

        dto.setId(estudiante.getId());

        dto.setNombre(estudiante.getNombre());

        dto.setApellido(estudiante.getApellido());

        dto.setCorreo(estudiante.getCorreo());

        return dto;
    }

    private Estudiante convertToEntity(
            EstudianteDTO dto
    ) {

        Estudiante estudiante = new Estudiante();

        estudiante.setId(dto.getId());

        estudiante.setNombre(dto.getNombre());

        estudiante.setApellido(dto.getApellido());

        estudiante.setCorreo(dto.getCorreo());

        return estudiante;
    }
}