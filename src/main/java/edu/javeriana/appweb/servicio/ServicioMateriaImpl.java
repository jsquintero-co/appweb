package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.MateriaDTO;
import edu.javeriana.appweb.modelo.Materia;
import edu.javeriana.appweb.repositorio.RepositorioMateria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServicioMateriaImpl
        implements ServicioMateria {

    @Autowired
    private RepositorioMateria repositorioMateria;

    @Override
    public Flux<MateriaDTO> findAll() {

        return repositorioMateria.findAll()
                .map(this::convertToDTO);
    }

    @Override
    public Mono<MateriaDTO> findById(Long id) {

        return repositorioMateria.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public Mono<MateriaDTO> save(MateriaDTO dto) {

        Materia materia = convertToEntity(dto);

        return repositorioMateria
                .save(materia)
                .map(this::convertToDTO);
    }

    @Override
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
    public Mono<Void> deleteById(Long id) {

        return repositorioMateria.deleteById(id);
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