package es.upm.sos.biblioteca.controllers;

import es.upm.sos.biblioteca.Excepciones.Prestamos.FechasNoValidasException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.LibroNoDisponibleException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoDevueltoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoVerificadoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioDevolucionesPendientesException;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.models.PrestamoModelAssembler;
import es.upm.sos.biblioteca.services.ServicioPrestamos;
import jakarta.validation.Valid;

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

import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoFechaDevolucionNoCoincideException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoFechaPrestamoNoCoincideException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioNoCoincideException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.UsuarioSancionadoException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.LibroNoCoincideException;

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

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPrestamo(@PathVariable int id) {
        try {
            Prestamo prestamo = servicio.getPrestamoId(id);
            return ResponseEntity.ok(prestamoModelAssembler.toModel(prestamo));
        } catch (PrestamoNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Object> devolverLibro_verificarDevolucion(@PathVariable int id, @Valid @RequestBody Prestamo prestamo){

        if(prestamo.isDevuelto()){
            //parte a devolver
            try{
                servicio.devolverLibro(id, prestamo);
                return ResponseEntity.noContent().build();
            }
            catch (PrestamoNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
            } catch (PrestamoDevueltoException e) {
             return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
            } catch (PrestamoFechaPrestamoNoCoincideException | PrestamoFechaDevolucionNoCoincideException | UsuarioNoCoincideException | LibroNoCoincideException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            }

        } else{
            //parte de verificar devolucion
            try{
                servicio.verificarDevolucion(id, prestamo);
                return ResponseEntity.noContent().build();
            }
            catch(PrestamoVerificadoException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
            } catch (PrestamoNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
            } catch (PrestamoFechaPrestamoNoCoincideException | PrestamoFechaDevolucionNoCoincideException | UsuarioNoCoincideException | LibroNoCoincideException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
            }

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
        } catch (LibroNoDisponibleException | UsuarioSancionadoException | UsuarioDevolucionesPendientesException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}