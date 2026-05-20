package edu.javeriana.appweb.controlador;

import edu.javeriana.appweb.dto.EstudianteDTO;
import edu.javeriana.appweb.dto.MateriaDTO;
import edu.javeriana.appweb.servicio.ServicioMateria;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/materias")
@CrossOrigin("*")
public class ControladorMateria {

    @Autowired
    private ServicioMateria servicio;

    @GetMapping
    public Flux<MateriaDTO> getAll() {

        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Mono<MateriaDTO> getById(
            @PathVariable Long id) {

        return servicio.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MateriaDTO> create(
            @Valid @RequestBody MateriaDTO dto) {

        return servicio.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<MateriaDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody MateriaDTO dto) {

        return servicio.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(
            @PathVariable Long id) {

        return servicio.deleteById(id);
    }

    @GetMapping("/{id}/estudiantes")
    public Flux<Long> obtenerEstudiantesPorMateria(
        @PathVariable Long id){

    return servicio.obtenerIdsEstudiantesPorMateria(id);
    }
}