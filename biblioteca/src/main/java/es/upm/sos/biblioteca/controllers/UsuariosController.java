package es.upm.sos.biblioteca.controllers;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.sos.biblioteca.models.Usuario;
import lombok.*;

@RestController
@RequestMapping("/biblioteca.api/users")
@AllArgsConstructor
public class UsuariosController {

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
}