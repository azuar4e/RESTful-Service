package es.upm.sos.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import es.upm.sos.biblioteca.modelos.Libros;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/biblioteca.api/Libros")

public class LibrosController{


    @GetMapping
    public ResponseEntity<List<Libros>> getLibros(){
        List<Libros> libros = new ArrayList<Libros>();
        
        //devuelve todos los libros 

        return ResponseEntity.ok(libros);
    }

    @GetMapping(params = "titulo_contiene")
    public ResponseEntity<List<Libros>> getLibrosContenido(
                                        @RequestParam String titulo_contiene){
        List<Libros> libros = new ArrayList<Libros>();
        
        //devuelve los libros que contengan en su titulo el parametro dicho

        return ResponseEntity.ok(libros);
    }

    @GetMapping(params = "disponible")
    //distinto metodo para cuando haya parametro titulo_contiene
    public ResponseEntity<List<Libros>> getLibrosDisponibles(
                                        @RequestParam boolean disponible){
        List<Libros> libros = new ArrayList<Libros>();
        
        //devuelve todos los libros disponibles 
        
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{Isbn}")
    public ResponseEntity<Libros> getLibroIsbn(@PathVariable String Isbn){
        Libros libro = new Libros();
       
       //devuelve un libro a partir de su isbn

        return ResponseEntity.ok(libro);
    } 

     @PostMapping
    public ResponseEntity<Object> añadirLibro(@RequestBody Libros libro){
        
        //añade el libro que recibe el metodo

        return ResponseEntity.created(linkTo(methodOn(LibrosController.class).
                                getLibroIsbn(libro.getIsbn())).toUri()).build();
    }

    @DeleteMapping("/{Isbn}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable String Isbn){
        
        //elimina un libro por su ISBN

        return ResponseEntity.noContent().build();
    }
}
