package es.upm.sos.biblioteca.controllers;

import java.util.ArrayList;
import java.util.List;

import es.upm.sos.biblioteca.models.Libros;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/biblioteca.api/Libros")

public class LibrosController{

    private final ServicioLibros servicio;

    @GetMapping
    public ResponseEntity<List<Libros>> getLibros(){
        //devuelve todos los libros  
        List<Libros> libros;

        libros = servicio.getLibros();

        return ResponseEntity.ok(libros);
    }

    @GetMapping(params = "tituloContiene")
    public ResponseEntity<List<Libros>> getLibrosContenido(
                                        @RequestParam String tituloContiene){
        //devuelve los libros que contengan en su titulo el parametro dicho
        List<Libros> libros;

        libros = servicio.getLibrosContenido(tituloContiene);

        return ResponseEntity.ok(libros);
    }

    @GetMapping(params = "disponible")
    //distinto metodo para cuando haya parametro titulo_contiene
    public ResponseEntity<List<Libros>> getLibrosDisponibles(
                                        @RequestParam boolean disponible){
       //devuelve todos los libros disponibles 
        List<Libros> libros;
        
        
        
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{Isbn}")
    public ResponseEntity<Libros> getLibroIsbn(@PathVariable String isbn){
        //devuelve un libro a partir de su isbn
        Libros libro = new Libros();

        servicio.getLibroIsbn(isbn);
        
        return ResponseEntity.ok(libro);
    } 

     @PostMapping
    public ResponseEntity<Object> añadirLibro(@RequestBody Libros libro){
    //añade el libro que recibe el metodo
    
        Libro libropost = servicio.postLibro(libro);

        return ResponseEntity.created(linkTo(methodOn(LibrosController.class).
                                getLibroIsbn(libro.getIsbn())).toUri()).build();
    }

    @DeleteMapping("/{Isbn}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable String Isbn){
        //elimina un libro por su ISBN

        servicio.deleteLibro(isbn);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{Isbn}")
    public ResponseEntity<Void> modificarLibro(@PathVariable String isbn, @RequestBody Libros libro){

        
        return ResponseEntity.noContent();
    }
}
