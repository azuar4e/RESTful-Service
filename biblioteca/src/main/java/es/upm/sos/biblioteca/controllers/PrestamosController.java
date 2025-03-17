package es.upm.sos.biblioteca.controllers;


import java.util.*;
import java.time.LocalDate;

import es.upm.sos.biblioteca.Excepciones.Prestamos.FechaDevolucionException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.models.PrestamoModelAssembler;
import es.upm.sos.biblioteca.services.ServicioPrestamos;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/prestamos")
@AllArgsConstructor
public class PrestamosController {
    private ServicioPrestamos servicio;
    private PagedResourcesAssembler<Prestamo> pagedResourcesAssembler;
    private PrestamoModelAssembler prestamoModelAssembler;

    //obtiene el prestamo a partir de la matricula y el isbn
    @GetMapping(params = {"matricula", "isbn"})
    public ResponseEntity<Object> getPrestamosMatriculaIsbn (@RequestParam String matricula,
        @RequestParam String isbn) {

        try {
            Prestamo prestamo = servicio.getPrestamoMatriculaIsbn(matricula, isbn);
            prestamo.add(linkTo(methodOn(PrestamosController.class).getPrestamosMatriculaIsbn(prestamo.getUsuario().getMatricula(), prestamo.getLibro().getIsbn())).withSelfRel());
            return ResponseEntity.ok(prestamoModelAssembler.toModel(prestamo));
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(params = "pId")
    public ResponseEntity<Object> getPrestamo(@RequestParam int pId) {
        try {
            Prestamo prestamo = servicio.getPrestamoId(pId);
            return ResponseEntity.ok(prestamoModelAssembler.toModel(prestamo));
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(params = "matricula")
    public ResponseEntity<Object> getPrestamosMatricula(@RequestParam String matricula,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "3", required = false) int size) {
        try {
            Page<Prestamo> prestamos = servicio.getPrestamosMatricula(matricula, page, size);
            return ResponseEntity.ok(pagedResourcesAssembler.toModel(prestamos, prestamoModelAssembler));
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(params = {"matricula", "fechaPrestamo"})
    public ResponseEntity<Object> getPrestamosPorFechaPrestamo(@RequestParam String matricula,
        @RequestParam LocalDate fechaPrestamo,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "3", required = false) int size) {
        try {
            Page<Prestamo> prestamos = servicio.getPrestamosPorFechaPrestamo(matricula, fechaPrestamo, page, size);
            return ResponseEntity.ok(pagedResourcesAssembler.toModel(prestamos, prestamoModelAssembler));
        } catch (PrestamoNotFoundContentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(params = {"matricula", "fechaDevolucion"})
    public ResponseEntity<Object> getPrestamosPorFechaDevolucion(@RequestParam String matricula,
        @RequestParam LocalDate fechaDevolucion,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "3", required = false) int size) {
        try {
            Page<Prestamo> prestamos = servicio.getPrestamosPorFechaDevolucion(matricula, fechaDevolucion, page, size);
            return ResponseEntity.ok(pagedResourcesAssembler.toModel(prestamos, prestamoModelAssembler));
        } catch (PrestamoNotFoundContentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(params = {"matricula", "fechaPrestamo", "fechaDevolucion"})
    public ResponseEntity<Object> getPrestamosPorFechaDevolucionPorFechaPrestamos(@RequestParam String matricula,
        @RequestParam LocalDate fechaPrestamo,
        @RequestParam LocalDate fechaDevolucion,
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "3", required = false) int size) {
        try {
            Page<Prestamo> prestamos = servicio.getPrestamosPorFechaDevolucionPorFechaPrestamos(matricula, fechaPrestamo, fechaDevolucion, page, size);
            return ResponseEntity.ok(pagedResourcesAssembler.toModel(prestamos, prestamoModelAssembler));
        } catch (PrestamoNotFoundContentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarFechaDevolucion(@PathVariable int id, @RequestBody Prestamo prestamo){
        try{
            servicio.actualizarFechaDevolucion(id, prestamo.getFechaDevolucion());
            return ResponseEntity.noContent().build();
        }
        catch(FechaDevolucionException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePrestamo(@PathVariable int id) {
        try{
            servicio.deletePrestamo(id);
            return ResponseEntity.noContent().build();
        }   
        catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }

    @PostMapping
    public ResponseEntity<Object> postPrestamo(@RequestBody Prestamo prestamo) {
        try{
            servicio.postPrestamo(prestamo);
            return ResponseEntity.created(linkTo(methodOn(PrestamosController.class).
                                            getPrestamo(prestamo.getId())).toUri()).build();
        }   
        catch (PrestamoConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); 
        }
    }
}