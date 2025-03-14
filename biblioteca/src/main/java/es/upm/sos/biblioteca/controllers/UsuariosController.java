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

    @PostMapping("/users")
    
    public void creaUsuario (@RequestBody Usuario user) {
    /*    user.setMatricula("");
        user.setNombre();
        user.setFecha();
        user.setEmail();
    */ 
    }

    @GetMapping("/{matricula}")
        public ResponseEntity<Usuario> getUsuario(@PathVariable String matricula){
        Usuario usuario = new Usuario();

        //cuerpo faltante
        //
        
        return ResponseEntity.ok(usuario);
        }


  /*  @PostMapping()
    ResponseEntity<Void> nuevoUsuario(@Valid @RequestBody Usuario nuevoUsuario) {
    if (!ServicioUsuarios.getUsuario(nuevoUsuario.getMatricula())) {
        Usuario usuario = ServicioUsuarios.postUsuario(nuevoUsuario);
        return ResponseEntity.created(linkTo(UsuariosController.class).slash(usuario.getMatricula()).toUri()).build();
        }
        throw new UsuarioExisteException(usuario.getMatricula());
    }
    */
}