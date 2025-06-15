package es.upm.sos.biblioteca.controllers;


import java.util.*;
import java.time.LocalDate;

import es.upm.sos.biblioteca.Excepciones.Prestamos.FechaDevolucionException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.FechasNoValidasException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.LibroNoDisponibleException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoDevueltoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoVerificadoException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





@RestController
@RequestMapping("/biblioteca.api/prestamos")
@AllArgsConstructor
public class PrestamosController {
    private ServicioPrestamos servicio;
    private PagedResourcesAssembler<Prestamo> pagedResourcesAssembler;
    private PrestamoModelAssembler prestamoModelAssembler;
    private static final Logger logger = LoggerFactory.getLogger(PrestamosController.class);

    @GetMapping
    public ResponseEntity<Object> getPrestamos(
        @RequestParam(defaultValue = "0", required = false) int page,
        @RequestParam(defaultValue = "3", required = false) int size) {
        Page<Prestamo> prestamos = servicio.getPrestamos(page, size);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(prestamos, prestamoModelAssembler));
    }
    

    //obtiene el prestamo a partir de la matricula y el isbn

    @GetMapping("/{matricula}/ultimos-libros-devueltos")
    public ResponseEntity<Object> getUltimosLibrosDevueltos(@PathVariable String matricula,
    @RequestParam(defaultValue = "0", required = false) int page,
    @RequestParam(defaultValue = "5", required = false) int size) {
        try {
            Page<Prestamo> prestamo = servicio.getUltimosLibrosDevueltos(matricula, page, size);
            return ResponseEntity.ok(pagedResourcesAssembler.toModel(prestamo, prestamoModelAssembler));
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getPrestamo(@PathVariable int id) {
        try {
            Prestamo prestamo = servicio.getPrestamoId(id);
            return ResponseEntity.ok(prestamoModelAssembler.toModel(prestamo));
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
/************************************************************************
************************************************************************
*************************************************************************/
    @PutMapping("/{id}/actualizar-devolucion")
    public ResponseEntity<Object> actualizarFechaDevolucion(@PathVariable int id, @RequestParam LocalDate fecha_devolucion){
        try{
            servicio.actualizarFechaDevolucion(id, fecha_devolucion);
            return ResponseEntity.noContent().build();
        }
        catch(FechaDevolucionException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

/************************************************************************
************************************************************************
*************************************************************************/
    @PutMapping("/{id}/devolucion")
    public ResponseEntity<Object> devolverLibro(@PathVariable int id){
        try{
            servicio.devolverLibro(id);
            return ResponseEntity.noContent().build();
        }
        catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (PrestamoDevueltoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @PutMapping("/{id}/verificar-devolucion")
    public ResponseEntity<Object> verificarDevolucion(@PathVariable int id){
        try{
            servicio.verificarDevolucion(id);
            return ResponseEntity.noContent().build();
        }
        catch(PrestamoVerificadoException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
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
            logger.info("Se llamo al endpoint /prestamo");
            return ResponseEntity.created(linkTo(methodOn(PrestamosController.class).
                                            getPrestamo(prestamo.getId())).toUri()).build();
        }   
        catch (PrestamoConflictException e) {
            logger.info("Ha fallado el endpoint /prestamo");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); 
        }
        catch (FechasNoValidasException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());  
        } catch (LibroNoDisponibleException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());  
        }
    }
}