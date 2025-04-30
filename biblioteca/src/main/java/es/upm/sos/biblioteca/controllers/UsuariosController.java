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
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.CorreoRegistradoException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.PrestamoYaEnListaException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.models.UsuarioModelAssembler;
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
        private String fechaNacimiento;
        private LocalDate sancion;
        private int porDevolver;
        private Page<Prestamo> prestamosActuales;
        private Page<Prestamo> historialPrestamos;
    }

    private ServicioUsuarios servicioUsuarios;
    private ServicioPrestamos servicioPrestamos;
    private PagedResourcesAssembler<Usuario> pagedResourcesAssembler;
    private UsuarioModelAssembler usuarioModelAssembler;

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
            actividadUsuario.setFechaNacimiento(usuario.getFechaNacimiento());
            actividadUsuario.setSancion(usuario.getSancion());
            actividadUsuario.setPorDevolver(usuario.getPorDevolver());
            actividadUsuario.setPrestamosActuales(servicioPrestamos.getPrestamosActuales(matricula, 0, 5));
            actividadUsuario.setHistorialPrestamos(servicioPrestamos.getUltimosLibrosDevueltos(matricula, 0, 5));
            EntityModel<ActividadUsuario> respuesta = EntityModel.of(actividadUsuario);

            respuesta.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UsuariosController.class).getUsuarioActividad(matricula)).withSelfRel());
            return ResponseEntity.ok(respuesta);
        }
        catch(UsuarioNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }


    @PostMapping
    public ResponseEntity<Object> añadirUsuario(@RequestBody Usuario usuario){ 
        try{
                Usuario nuevo = servicioUsuarios.postUsuario(usuario);
                return ResponseEntity.created(linkTo(methodOn(UsuariosController.class).
                                            getUsuario(nuevo.getMatricula())).toUri()).build();
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
    
}