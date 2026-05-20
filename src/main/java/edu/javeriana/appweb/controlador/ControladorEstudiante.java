package edu.javeriana.appweb.controlador;

import edu.javeriana.appweb.dto.EstudianteDTO;
import edu.javeriana.appweb.servicio.ServicioEstudiante;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin("*")
public class ControladorEstudiante {

    @Autowired
    private ServicioEstudiante servicio;

    @GetMapping
    public Flux<EstudianteDTO> listar() {

        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Mono<EstudianteDTO> buscarPorId(
            @PathVariable Long id) {

        return servicio.findById(id);
    }

    @PostMapping
    public Mono<EstudianteDTO> guardar(
            @Valid @RequestBody EstudianteDTO dto) {

        return servicio.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<EstudianteDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteDTO dto) {

        return servicio.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(
            @PathVariable Long id) {

        return servicio.deleteById(id);
    }

@GetMapping("/{estudianteId}/nota-final/{materiaId}")
public Mono<Double> calcularNotaFinal(

        @PathVariable Long estudianteId,

        @PathVariable Long materiaId
){

    return servicio.calcularNotaFinal(
            estudianteId,
            materiaId
    );
}
}