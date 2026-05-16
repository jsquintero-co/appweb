package edu.javeriana.appweb.controlador;

import edu.javeriana.appweb.dto.NotaDTO;
import edu.javeriana.appweb.servicio.ServicioNota;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notas")
@CrossOrigin("*")
public class ControladorNota {

    @Autowired
    private ServicioNota servicio;

    @GetMapping
    public Flux<NotaDTO> getAll() {

        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Mono<NotaDTO> getById(@PathVariable Long id) {

        return servicio.findById(id);
    }

    @GetMapping("/estudiante/{id}")
    public Flux<NotaDTO> getByEstudiante(@PathVariable Long id) {

        return servicio.findByEstudianteId(id);
    }

    @PostMapping
    public Mono<NotaDTO> create(@Valid @RequestBody NotaDTO dto) {

        return servicio.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<NotaDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody NotaDTO dto) {

        return servicio.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {

        return servicio.deleteById(id);
    }

    @GetMapping("/estudiante/{id}/nota-final")
    public Mono<Double> calcularNotaFinal(
        @PathVariable Long id){

        return servicio.calcularNotaFinal(id);
    }
}