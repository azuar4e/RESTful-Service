package es.upm.sos.biblioteca.controllers;

import es.upm.sos.biblioteca.models.Prestamos;
import es.upm.sos.biblioteca.repository.PrestamosRepository;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/prestamos")
public class PrestamosControllers {

    @GetMapping(value = "/users/{matricula}")
    public ResponseEntity<List<Prestamos>> getPrestamosUsuario(@PathVariable int matricula){
        List<Prestamos> prestamos;

        prestamos = PrestamosRepository.getPrestamosMatricula(matricula);

        return ResponseEntity.ok(prestamos);
    }

    @GetMapping(value = "/users/{matricula}")
    public ResponseEntity<List<Prestamos>> getPrestamosUsuario(@PathVariable int matricula, @RequestParam(required = false) String fecha){

        List<Prestamos> prestamos;

        if (fecha == null) { prestamos = ParametrosRepository.getPrestamosMatricula(matricula); } 
        else { prestamos = ParametrosRepository.getPrestamosMatriculayFecha(matricula, fecha); }
        
        return ResponseEntity.ok(prestamos);
    }
}