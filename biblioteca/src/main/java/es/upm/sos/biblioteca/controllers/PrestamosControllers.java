package es.upm.sos.biblioteca.controllers;

import es.upm.sos.biblioteca.modelos.Prestamos;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/prestamos")
public class PrestamosControllers {

    @GetMapping(value = "/users/{matricula}")
    public ResponseEntity<List<Prestamos>> getPrestamosUsuario(@PathVariable int matricula){
        List<Prestamos> prestamos = new ArrayList<Prestamos>();

        return prestamos;
    }

}