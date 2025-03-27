package es.upm.sos.biblioteca.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

//import javax.validation.Valid;
import es.upm.sos.biblioteca.services.ServicioUsuarios;

import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import es.upm.sos.biblioteca.models.Prestamo;
import es.upm.sos.biblioteca.Excepciones.Prestamos.PrestamoConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.PrestamoYaEnListaException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioConflictException;
import es.upm.sos.biblioteca.Excepciones.Usuarios.UsuarioNotFoundException;
import es.upm.sos.biblioteca.models.UsuarioModelAssembler;
import es.upm.sos.biblioteca.models.Usuario;
import lombok.*;


@RestController
@RequestMapping("/biblioteca.api/users")
@AllArgsConstructor
public class UsuariosController {

private ServicioUsuarios servicioUsuarios;
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
    }

    @PostMapping("/{matricula}/prestamos")//probar este metodo y ver la uri que devuelve
    public ResponseEntity<Object> nuevoPrestamo(@PathVariable String matricula, @RequestBody Prestamo prestamo){
        try{
            Prestamo nuevo = servicioUsuarios.postPrestamoUsuario(matricula,prestamo);
            return ResponseEntity.created(linkTo(methodOn(UsuariosController.class).
                                        getUsuario(matricula)).linkTo(prestamo.getId()).toUri()).build();
        }
        catch(UsuarioNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
        catch(PrestamoConflictException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); 
        }
    }

//FALTA DELETE MAPING DE PRESTAMO DEL USUARIO
    

    @PutMapping("/{matricula}")
    //actualiza el libro por el isbn
    public ResponseEntity<Object> modificarUsuario(@PathVariable String matricula, @RequestBody Usuario usuario){
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