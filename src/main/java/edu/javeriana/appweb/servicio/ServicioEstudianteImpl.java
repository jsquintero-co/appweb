package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.EstudianteDTO;
import edu.javeriana.appweb.modelo.Estudiante;
import edu.javeriana.appweb.repositorio.RepositorioEstudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        public Mono<Double> calcularNotaFinal(Long estudianteId){

         return servicioNota
               .calcularNotaFinal(estudianteId);
        }
    @Override
    public Flux<EstudianteDTO> findAll() {

        return repositorio.findAll()
                .map(this::convertToDTO);
    }

    @Override
    public Mono<EstudianteDTO> findById(Long id) {

        return repositorio.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public Mono<EstudianteDTO> save(EstudianteDTO dto) {

        return repositorio.findByCorreo(dto.getCorreo())

                .flatMap(existente ->
                        Mono.<EstudianteDTO>error(
                                new RuntimeException(
                                        "El correo ya existe"
                                )
                        )
                )

                .switchIfEmpty(

                        repositorio.save(
                                        convertToEntity(dto)
                                )
                                .map(this::convertToDTO)
                );
    }

    @Override
    public Mono<EstudianteDTO> update(Long id, EstudianteDTO dto) {

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
    public Mono<Void> deleteById(Long id) {

        return repositorio.deleteById(id);
    }

    private EstudianteDTO convertToDTO(Estudiante e) {

        EstudianteDTO dto = new EstudianteDTO();

        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setApellido(e.getApellido());
        dto.setCorreo(e.getCorreo());

        return dto;
    }

    private Estudiante convertToEntity(EstudianteDTO dto) {

        Estudiante e = new Estudiante();

        e.setId(dto.getId());
        e.setNombre(dto.getNombre());
        e.setApellido(dto.getApellido());
        e.setCorreo(dto.getCorreo());

        return e;
    }
}