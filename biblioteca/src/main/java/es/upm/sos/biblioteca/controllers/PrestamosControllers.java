package es.upm.sos.biblioteca.controllers;

import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.services.ServicioPrestamos;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/prestamos")
@AllArgsConstructor
public class PrestamosControllers {
    private ServicioPrestamos servicio;

/* no se pueden dos metodos llamando a la misma url de esta forma

    @GetMapping(value = "/users/{matricula}")
    public ResponseEntity<Object> getPrestamosUsuario(@PathVariable int matricula){
        Optional<List<Prestamo>> prestamos;

        prestamos = servicio.getPrestamosMatricula(matricula);

        return ResponseEntity.ok(prestamos);
    }
*/
    @GetMapping(value = "/users/{matricula}")
    public ResponseEntity<Object> getPrestamosUsuarioFiltrado(@PathVariable int matricula, @RequestParam(required = false) String fecha){

        Optional<List<Prestamo>> prestamos;

        if (fecha == null) { prestamos = servicio.getPrestamosMatricula(matricula); } 
        else { prestamos = servicio.getPrestamosMatriculayFecha(matricula, fecha); }
        
        return ResponseEntity.ok(prestamos);
    }
}