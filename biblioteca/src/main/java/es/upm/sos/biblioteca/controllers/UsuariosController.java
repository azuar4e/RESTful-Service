package es.upm.sos.biblioteca.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.time.LocalDate;

import es.upm.sos.biblioteca.services.ServicioPrestamos;
//import javax.validation.Valid;
import es.upm.sos.biblioteca.services.ServicioUsuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.CorreoRegistradoException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.Excepciones.Prestamos.FechaDevolucionException;
import es.upm.sos.biblioteca.models.UsuarioModelAssembler;
import es.upm.sos.biblioteca.models.PrestamoModelAssembler;
import es.upm.sos.biblioteca.models.Usuario;
import lombok.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/biblioteca.api/users")
@AllArgsConstructor
public class UsuariosController {

    @Data // Lombok genera automáticamente los getters, setters, equals, hashCode, y toString
    @NoArgsConstructor // Constructor vacío
    @AllArgsConstructor // Constructor con todos los campos
    class ActividadUsuario {
        private String matricula;
        private String nombre;
        private String correo;
        private String fecha_nacimiento;
        private LocalDate sancion;
        private int por_devolver;
        private Page<Prestamo> prestamos_actuales;
        private Page<Prestamo> historial_prestamos;
    }

    private ServicioUsuarios servicioUsuarios;
    private ServicioPrestamos servicioPrestamos;
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;
    private PagedResourcesAssembler<Prestamo> prestamoResourcesAssembler;
    private UsuarioModelAssembler usuarioModelAssembler;
    private PrestamoModelAssembler prestamoModelAssembler;

    @GetMapping
    public ResponseEntity<Object> getUsuarios( 
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "3", required = false) int size){
        Page<Usuario> usuarios = servicioUsuarios.getUsuarios(page, size);
        return new ResponseEntity<>(pagedResourcesAssembler.toModel(usuarios, usuarioModelAssembler),HttpStatus.OK);
    }
    

    @GetMapping("/{matricula}")
        public ResponseEntity<Object> getUsuario(@PathVariable String matricula){
        try{
            Usuario usuario = servicioUsuarios.getUsuario(matricula);
            usuario.add(linkTo(methodOn(UsuariosController.class).getUsuario(matricula)).withSelfRel());
            return ResponseEntity.ok(usuario);
        }
        catch(UsuarioNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/{matricula}/actividad")
    public ResponseEntity<Object> getUsuarioActividad(@PathVariable String matricula){
        try{
            Usuario usuario = servicioUsuarios.getUsuario(matricula);
            ActividadUsuario actividadUsuario = new ActividadUsuario(matricula, null, null, null, null, 0, null, null);
            actividadUsuario.setNombre(usuario.getNombre());
            actividadUsuario.setCorreo(usuario.getCorreo());
            actividadUsuario.setFecha_nacimiento(usuario.getFecha_nacimiento());
            actividadUsuario.setSancion(usuario.getSancion());
            actividadUsuario.setPor_devolver(usuario.getPor_devolver());
            actividadUsuario.setPrestamos_actuales(servicioPrestamos.getPrestamosActuales(matricula, 0, 5));
            actividadUsuario.setHistorial_prestamos(servicioPrestamos.getUltimosLibrosDevueltos(matricula, 0, 5));
            EntityModel<ActividadUsuario> respuesta = EntityModel.of(actividadUsuario);

            respuesta.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuariosController.class).getUsuarioActividad(matricula)).withSelfRel());
            return ResponseEntity.ok(respuesta);
        }
        catch(UsuarioNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> añadirUsuario(@RequestBody Usuario usuario){ 
        try{
                Usuario nuevo = servicioUsuarios.postUsuario(usuario);
                return ResponseEntity.created(linkTo(methodOn(UsuariosController.class)
                            .getUsuario(nuevo.getMatricula())).toUri())
                            .body(nuevo);
            }
        catch(UsuarioConflictException e){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
            }
        catch(CorreoRegistradoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Object> modificarUsuario(@PathVariable String matricula, @Valid @RequestBody Usuario usuario){
        try{
            servicioUsuarios.actualizarUsuario(matricula, usuario);
            return ResponseEntity.noContent().build();
        }
        catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }


    @DeleteMapping("/{matricula}")
        ResponseEntity<Object> eliminarUsuario(@PathVariable String matricula){
        //elimina un usuario por su atricula
        try{
            servicioUsuarios.deleteUsuario(matricula);
            return ResponseEntity.noContent().build();
        }   
        catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }

    @GetMapping("/{matricula}/prestamos")
    public ResponseEntity<Object> getPrestamosPorFecha(
        @PathVariable String matricula,
        @RequestParam(required = false) Boolean devuelto,
        @RequestParam(required = false) LocalDate fecha_prestamo,
        @RequestParam(required = false) LocalDate fecha_devolucion,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size) {

        try {
            //sin ningun tipo de filtrado
            if (devuelto != null) {
                Page prestamos = servicioUsuarios.getHistorico(matricula,devuelto, page,size);
                return new ResponseEntity<>(prestamoResourcesAssembler.toModel(prestamos, prestamoModelAssembler),HttpStatus.OK);
            } else {
                if (fecha_prestamo == null && fecha_devolucion == null) {
                    Page prestamos = servicioUsuarios.getPrestamosMatricula(matricula, page, size);
                    return new ResponseEntity<>(prestamoResourcesAssembler.toModel(prestamos, prestamoModelAssembler),HttpStatus.OK);
                
                } else {
                    if (fecha_prestamo != null && fecha_devolucion != null) {
                        Page prestamos = servicioUsuarios.getPrestamosPorDobleFiltrado(matricula,fecha_prestamo,fecha_devolucion,page,size);
                        return new ResponseEntity<>(prestamoResourcesAssembler.toModel(prestamos, prestamoModelAssembler),HttpStatus.OK);

                    
                    //filtrado solo inicio prestamo
                    } else if (fecha_prestamo != null && fecha_devolucion==null) {
                        Page prestamos = servicioUsuarios.getPrestamosPorFechaPrestamo(matricula, fecha_prestamo, page, size);
                        return new ResponseEntity<>(prestamoResourcesAssembler.toModel(prestamos, prestamoModelAssembler),HttpStatus.OK);
                    
                    //filtrado solo fecha devolucion
                    } else if(fecha_prestamo == null && fecha_devolucion!=null){
                        Page prestamos = servicioUsuarios.getPrestamosPorFechaDevolucion(matricula, fecha_devolucion, page, size);
                        return new ResponseEntity<>(prestamoResourcesAssembler.toModel(prestamos, prestamoModelAssembler),HttpStatus.OK);
                        
                    } else{
                        return ResponseEntity.badRequest().body("Parámetros inválidos.");
                    }
                }
            }
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{matricula}/prestamos/{idprestamo}")
    public ResponseEntity<Object> actualizarPrestamo(
        @PathVariable String matricula,
        @PathVariable int idprestamo,
        @Valid @RequestBody Prestamo prestamo
    ){  //si ampliar = false bad request
        if(!prestamo.isAmpliar()){ return ResponseEntity.badRequest().build(); }
        
        try{
            servicioUsuarios.ampliarPrestamo(matricula, idprestamo);
            return ResponseEntity.noContent().build();
        } catch(UsuarioNotFoundException | PrestamoNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(FechaDevolucionException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}