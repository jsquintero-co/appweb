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
    public Flux<NotaDTO> listar(){

        return servicio.findAll();
    }

    @GetMapping("/{id}")
    public Mono<NotaDTO> buscarPorId(
            @PathVariable Long id){

        return servicio.findById(id);
    }

    @GetMapping("/estudiante/{estudianteId}")
    public Flux<NotaDTO> listarPorEstudiante(
            @PathVariable Long estudianteId){

        return servicio.findByEstudianteId(estudianteId);
    }

    @PostMapping
    public Mono<NotaDTO> guardar(
            @Valid @RequestBody NotaDTO dto){

        return servicio.save(dto);
    }

    @PutMapping("/{id}")
    public Mono<NotaDTO> actualizar(

            @PathVariable Long id,

            @Valid @RequestBody NotaDTO dto
    ){

        return servicio.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(
            @PathVariable Long id){

        return servicio.deleteById(id);
    }

    @GetMapping("/final/{estudianteId}/{materiaId}")
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