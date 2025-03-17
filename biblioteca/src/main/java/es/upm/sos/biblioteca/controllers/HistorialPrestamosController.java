package es.upm.sos.biblioteca.controllers;

import es.upm.sos.biblioteca.services.ServicioHistorialPrestamos;
import es.upm.sos.biblioteca.models.HistorialPrestamos;
import es.upm.sos.biblioteca.Excepciones.HistorialPrestamos.*;
import java.time.LocalDate;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historial-prestamos")
@AllArgsConstructor
public class HistorialPrestamosController {

    @Autowired
    private ServicioHistorialPrestamos servicio;

    @GetMapping("/{matricula}")
    public ResponseEntity<Object> getHistorial(@PathVariable String matricula){
        try{
        List<HistorialPrestamos> historial = servicio.getHistorialPrestamosPorMatricula(matricula);
        return ResponseEntity.ok(historial);
        }
        catch(HistorialNotFound e){
            return ResponseEntity.notFound().build();
        }
    }

    //NOSE COMO PONER LOS PARAMETOS
    @GetMapping(params= {"matricula","fechaDevolucion"})
    public ResponseEntity<Object> getPrestamosPorFDevolucion(@RequestParam String matricula, @RequestParam LocalDate fechaDevolucion) {
        try{
        List<HistorialPrestamos> historial = servicio.getPrestamosPorFechaDevolucion(matricula,fechaDevolucion);
        return ResponseEntity.ok(historial);
        }
        catch(HistorialIsNull e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(params= {"matricula","fechaPrestamos"})
    public ResponseEntity<Object> getPrestamosPorFPrestamos(@RequestParam String matricula,@RequestParam LocalDate fechaPrestamos){
        try{
        List<HistorialPrestamos> historial = servicio.getPrestamosPorFechaPrestamo(matricula,fechaPrestamo);
        return ResponseEntity.ok(historial);
        }
        catch(HistorailIsNull e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params= {"matricula","fechaPrestamos","fechaDevolucion"})
    public ResponseEntity<Object> getPrestamosEntreFechas(@RequestParam String matricula, @RequestParam LocalDate fechaPrestamos, @RequestParam LocalDate fechaDevolucion){
        try{
        List<HistorialPrestamos> historial = servicio.getPrestamosEntreFechas(matricula, fechaPrestamos, fechaDevolucion);
        return ResponeEntity.ok(historial);
        }
        catch(HistorialNotFound e){
            return ResponseEntity.notFound().build();
        }
    }
}