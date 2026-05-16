package edu.javeriana.appweb.servicio;

import edu.javeriana.appweb.dto.NotaDTO;
import edu.javeriana.appweb.modelo.Materia;
import edu.javeriana.appweb.modelo.Nota;
import edu.javeriana.appweb.repositorio.RepositorioMateria;
import edu.javeriana.appweb.repositorio.RepositorioNota;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ServicioNotaImpl implements ServicioNota {

    @Autowired
    private RepositorioNota repositorioNota;

    @Autowired
    private RepositorioMateria repositorioMateria;

    @Override
    public Flux<NotaDTO> findAll() {

        return repositorioNota.findAll()
                .flatMap(this::convertToDTO);
    }

    @Override
    public Mono<NotaDTO> findById(Long id) {

        return repositorioNota.findById(id)
                .flatMap(this::convertToDTO);
    }

    @Override
    public Flux<NotaDTO> findByEstudianteId(Long estudianteId) {

        return repositorioNota.findByEstudianteId(estudianteId)
                .flatMap(this::convertToDTO);
    }

    @Override
    public Mono<NotaDTO> save(NotaDTO dto) {

        return repositorioMateria

                .findByNombre(dto.getMateriaNombre())

                .switchIfEmpty(

                        repositorioMateria.save(
                                new Materia(
                                        null,
                                        dto.getMateriaNombre(),
                                        3
                                )
                        )
                )

                .flatMap(materia -> {

                    Nota nota = new Nota();

                    nota.setValor(dto.getValor());

                    nota.setPorcentaje(dto.getPorcentaje());

                    nota.setObservacion(dto.getObservacion());

                    nota.setEstudianteId(dto.getEstudianteId());

                    nota.setMateriaId(materia.getId());

                    return repositorioNota.save(nota);
                })

                .flatMap(this::convertToDTO);
    }

    @Override
    public Mono<NotaDTO> update(Long id, NotaDTO dto) {

        return repositorioNota.findById(id)

                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException(
                                        "Nota no encontrada"
                                )
                        )
                )

                .flatMap(nota -> {

                    nota.setValor(dto.getValor());

                    nota.setPorcentaje(dto.getPorcentaje());

                    nota.setObservacion(dto.getObservacion());

                    return repositorioNota.save(nota);
                })

                .flatMap(this::convertToDTO);
    }

    @Override
    public Mono<Void> deleteById(Long id) {

        return repositorioNota.deleteById(id);
    }

    @Override
    public Mono<Double> calcularNotaFinal(Long estudianteId) {

        return repositorioNota

                .findByEstudianteId(estudianteId)

                .map(nota ->
                        (nota.getValor() * nota.getPorcentaje()) / 100.0
                )

                .reduce(0.0, Double::sum);
    }

    private Mono<NotaDTO> convertToDTO(Nota nota) {

        return repositorioMateria

                .findById(nota.getMateriaId())

                .map(materia -> {

                    NotaDTO dto = new NotaDTO();

                    dto.setId(nota.getId());

                    dto.setValor(nota.getValor());

                    dto.setPorcentaje(nota.getPorcentaje());

                    dto.setObservacion(nota.getObservacion());

                    dto.setEstudianteId(nota.getEstudianteId());

                    dto.setMateriaId(nota.getMateriaId());

                    dto.setMateriaNombre(materia.getNombre());

                    return dto;
                });
    }
}