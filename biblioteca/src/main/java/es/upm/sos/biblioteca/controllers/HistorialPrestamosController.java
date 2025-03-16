package es.upm.sos.biblioteca.controllers;


import java.time.LocalDate;

import es.upm.sos.biblioteca.models.HistorialPrestamos;
import es.upm.sos.biblioteca.services.ServicioHistorialPrestamos;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/historial-prestamos")
@AllArgsConstructor
public class HistorialPrestamosController {

    @Autowired
    private ServicioHistorialPrestamos service;

    @GetMapping("/{matricula}")
    public ResponseEntity<List<HistorialPrestamos>> getHistorial(@PathVariable String matricula){
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
    public ResponseEntity<List<HistorialPrestamos>> getPrestamosPorFDevolucion(@RequestParam String matricula, @RequestParam LocalDate fechaDevolucion) {
        try{
        List<HistorialPrestamos> historial = servicio.getPrestamosPorFechaDevolucion(matricula,fechaDevolucion);
        return ResponseEntity.ok(historial);
        }
        catch(HistorialIsNull e){
            return ResponseEntity.ok(historial);
        }
    }

    @GetMapping(params= {"matricula","fechaPrestamos"})
    public ResponseEntity<List<HistorialPrestamos>> getPrestamosPorFPrestamos(@RequestParam String matricula,@RequestParam LocalDate fechaPrestamos){
        try{
        List<HistorialPrestamos> historial = servicio.getPrestamosPorFechaPrestamo(matricula,fechaPrestamo);
        return ResponseEntity.ok(historial);
        }
        catch(HistorailIsNull e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params= {"matricula","fechaPrestamos","fechaDevolucion"})
    public ResponseEntity<List<HistorialPrestamos>> getPrestamosEntreFechas(@RequestParam String matricula, @RequestParam LocalDate fechaPrestamos, @RequestParam LocalDate fechaDevolucion){
        try{
        List<HistorialPrestamos> historial = servicio.getPrestamosEntreFechas(matricula, fechaPrestamos, fechaDevolucion);
        return ResponeEntity.ok(historial);
        }
        catch(HistorialNotFound e){
            return ResponseEntity.notFound().build();
        }
    }


}