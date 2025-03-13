package es.upm.sos.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.services.ServicioLibros;
import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/biblioteca.api/Libros")
@AllArgsConstructor
public class LibrosController{

    private final ServicioLibros servicio;

    /* @GetMapping("/prueba")
    public String prueba(){
        System.out.println("prueba pasada");
        return "prueba pasada";
    }
     */

    @GetMapping
    public ResponseEntity<Object> getLibros(){
        //devuelve todos los libros  
        Optional<List<Libro>> libros;

        libros = servicio.getLibros();

        return ResponseEntity.ok(libros);
    }
                                                                                                                                                        
    @GetMapping(params = "tituloContiene")
    public ResponseEntity<Object> getLibrosContenido(
                                        @RequestParam String tituloContiene){
        //devuelve los libros que contengan en su titulo el parametro dicho
        Optional<List<Libro>> libros;

        libros = servicio.getLibrosContenido(tituloContiene);

        return ResponseEntity.ok(libros);
    }

    @GetMapping(params = "disponible")
    //distinto metodo para cuando haya parametro titulo_contiene
    public ResponseEntity<Object> getLibrosDisponibles(
                                        @RequestParam boolean disponible){
       //devuelve todos los libros disponibles 
        List<Libro> libros = null;
        
        //cuerpo por hacer
        
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{Isbn}")
    public ResponseEntity<Libro> getLibroIsbn(@PathVariable String isbn){
        //devuelve un libro a partir de su isbn
        Libro libro = new Libro();

        servicio.getLibroIsbn(isbn);
        
        return ResponseEntity.ok(libro);
    } 

    @PostMapping
    public ResponseEntity<Object> añadirLibro(@RequestBody Libro libro){
    //añade el libro que recibe el metodo
    
        servicio.postLibro(libro);

        return ResponseEntity.created(linkTo(methodOn(LibrosController.class).
                                getLibroIsbn(libro.getIsbn())).toUri()).build();
    }

    @DeleteMapping("/{Isbn}")
    public ResponseEntity<Object> eliminarLibro(@PathVariable String isbn){
        //elimina un libro por su ISBN

        servicio.deleteLibro(isbn);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{Isbn}")
    public ResponseEntity<Object> modificarLibro(@PathVariable String isbn, @RequestBody Libro libro){

        
        return ResponseEntity.noContent().build();
    }
}
