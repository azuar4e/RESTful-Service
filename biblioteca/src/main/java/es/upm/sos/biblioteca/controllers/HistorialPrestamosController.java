package es.upm.sos.biblioteca.controllers;

import es.upm.sos.biblioteca.models.HistorialPrestamos;
import es.upm.sos.biblioteca.services.ServicioHistorialPrestamos;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/historial-prestamos")
@AllArgsConstructor
public class HistorialPrestamosControllers {

    @Autowired
    public HistorialPrestamosController(HistorialPrestamosService service) {
        this.service = service;
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<List<HistorialPrestamos>> getHistorial(@PathVariable String matricula){
        List<HistorialPrestamos> historial = servicio.getHistorialPrestamosPorMatricula(matricula);
        return ResponseEntity.ok(historial);
    }

    //NOSE COMO PONER LOS PARAMETOS
    @GetMapping(params= {"matricula","fechaDevolucion"})
    public ResponseEntity<List<HistorialPrestamos>> getPrestamosPorFDevolucion(@Pathvariable String matricula, @PathVariable LocalDate fechaDevolucion) {
        List<HistorialPrestamos> historial = servicio.getPrestamosPorFechaDevolucion(matricula,fechaDevolucion);
        return ResponseEntity.ok(historial);
    }

    @GetMapping(params= {"matricula","fechaPrestamo"})
    public ResponseEntity<List<HistorialPrestamos>> getPrestamosPorFPrestamos(@PathVariable String matricula,@PathVariable LocalDate fechaPrestamos){
        List<HistorialPrestamos> historial = servicio.getPrestamosPorFechaPrestamo(matricula,fechaPrestamo);
        return ResponseEntity.ok(historial);
    }


}