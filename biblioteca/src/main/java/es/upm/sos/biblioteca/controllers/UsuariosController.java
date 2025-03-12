package es.upm.sos.biblioteca.controllers;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.sos.biblioteca.modelos.Usuarios;

import es.upm.sos.biblioteca.modelos.Usuarios;

@RestController
@RequestMapping("/biblioteca.api/users")

public class UsuariosController {

    @PostMapping("/users")
    public void creaUsuario (@RequestBody Usuarios user) {
        user.setMatricula(""));
        user.setNombre();
        user.setFecha();
        user.setEmail();
    }

    @GetMapping("/{matricula}")
        public ResponseEntity<Usuarios> getUsuario(@PathVariable String matricula){
        Usuarios usuario = new Usuarios();
       
        //cuerpo faltante
        //
        
         return ResponseEntity.ok(usuario);
        }
}