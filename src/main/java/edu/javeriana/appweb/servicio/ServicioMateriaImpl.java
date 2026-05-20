package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.MateriaDTO;
import edu.javeriana.appweb.modelo.Materia;
import edu.javeriana.appweb.modelo.Nota;
import edu.javeriana.appweb.repositorio.RepositorioMateria;
import edu.javeriana.appweb.repositorio.RepositorioNota;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServicioMateriaImpl
        implements ServicioMateria {

    @Autowired
    private RepositorioMateria repositorioMateria;

    @Autowired
    private RepositorioNota repositorioNota;

    @Override
    public Flux<MateriaDTO> findAll() {

        return repositorioMateria.findAll()

                .limitRate(10)

                .map(this::convertToDTO);
    }

    @Override
    public Mono<MateriaDTO> findById(Long id) {

        return repositorioMateria.findById(id)

                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException(
                                        "Materia no encontrada"
                                )
                        )
                )

                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Mono<MateriaDTO> save(MateriaDTO dto) {

        return repositorioMateria

                .findByNombre(dto.getNombre())

                .flatMap(materiaExistente ->

                        Mono.error(
                                new RuntimeException(
                                        "La materia ya existe"
                                )
                        )
                )

                .switchIfEmpty(

                        repositorioMateria.save(
                                convertToEntity(dto)
                        )
                )

                .map(materia -> (Materia) materia)

                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Mono<MateriaDTO> update(Long id, MateriaDTO dto) {

        return repositorioMateria.findById(id)

                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException(
                                        "Materia no encontrada"
                                )
                        )
                )

                .flatMap(materia -> {

                    materia.setNombre(dto.getNombre());

                    materia.setCreditos(dto.getCreditos());

                    return repositorioMateria.save(materia);
                })

                .map(this::convertToDTO);
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(Long id) {

        return repositorioMateria.deleteById(id);
    }

    @Override
    public Flux<Long> obtenerIdsEstudiantesPorMateria(
            Long materiaId
    ) {

        return repositorioNota

                .findByMateriaId(materiaId)

                .map(Nota::getEstudianteId)

                .distinct();
    }

    private MateriaDTO convertToDTO(Materia materia) {

        MateriaDTO dto = new MateriaDTO();

        dto.setId(materia.getId());

        dto.setNombre(materia.getNombre());

        dto.setCreditos(materia.getCreditos());

        return dto;
    }

    private Materia convertToEntity(MateriaDTO dto) {

        Materia materia = new Materia();

        materia.setId(dto.getId());

        materia.setNombre(dto.getNombre());

        materia.setCreditos(dto.getCreditos());

        return materia;
    }
}