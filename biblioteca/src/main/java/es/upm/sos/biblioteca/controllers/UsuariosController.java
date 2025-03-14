package es.upm.sos.biblioteca.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//import javax.validation.Valid;
import es.upm.sos.biblioteca.services.ServicioUsuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.sos.biblioteca.models.Usuario;
import es.upm.sos.biblioteca.services.ServicioUsuarios;
import lombok.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsuariosController {

@Autowired
private ServicioUsuarios servicioUsuarios; 

    //Metodo POST que anade un usuario y linkea la URI a partir de la matricula
    @PostMapping
    public ResponseEntity<Object> anadirUsuario(@RequestBody Usuario usuario){
        Usuario user = servicio.postUsuario(usuario);
        return ResponseEntity.created(linkTo(methodOn(UsuariosController.class).getUsuario(user.getMatricula())).toUri()).build();
    }

    //Metodo GET para devolver un usuario a traves de su matricula
    @GetMapping("/{matricula}")
        public ResponseEntity<Usuario> getUsuario(@PathVariable String matricula){
        return ResponseEntity.ok(usuario.getusuario(matricula));
        }

    //Metodo GET para obtener todos los usuarios
    @GetMapping
    public ResponseEntity<Page<Usuario>> getUsuarios(Pageable pageable){
        Page<Usuario> usuarios = servicio.getUsuarios(pagebale);
        return ResponseEntity.ok(usuarios);
    }

   //Elimina un alumno por su matricula
   @DeleteMapping("/{matricula}")
   public ResponseEntity<Object> eliminarUsuario(@PathVariable String matricula){
        servicio.deleteUsuario(matricula);
        return ResponseEntity.noContent().build();
   }

    //metodo para borrar todos los usuarios para pruebas, eliminar al acabar codigo
    @DeleteMapping
    public ResponseEntity<Object> borrarTodo(){
        servicio.deleteTodos();
        return ResponseEntity.noContent().build();
    }
}