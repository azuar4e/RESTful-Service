package es.upm.sos.biblioteca.controllers;

import java.util.List;
import java.util.Optional;

import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.services.ServicioLibros;
import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
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
        List<Libro> libros;

        libros = servicio.getLibros();

        return ResponseEntity.ok(libros);
    }
                                                                                                                                                        
    @GetMapping(params = "tituloContiene")
    public ResponseEntity<Object> getLibrosContenido(
                                        @RequestParam String tituloContiene){
        //devuelve los libros que contengan en su titulo el parametro dicho
        List<Libro> libros;

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

    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> getLibroIsbn(@PathVariable String isbn){
        //devuelve un libro a partir de su isbn
        
        return ResponseEntity.ok(servicio.getLibroIsbn(isbn));
    } 

    @PostMapping
    public ResponseEntity<Object> añadirLibro(@RequestBody Libro libro){
        Libro nuevo = servicio.postLibro(libro);
        //linkea el nuevo libro a la uri creada por su isbn
        return ResponseEntity.created(linkTo(methodOn(LibrosController.class).
        getLibroIsbn(nuevo.getIsbn())).toUri()).build();

    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> eliminarLibro(@PathVariable String isbn){
        //elimina un libro por su ISBN

        servicio.deleteLibro(isbn);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Object> modificarLibro(@PathVariable String isbn, @RequestBody Libro libro){

        servicio.actualizarLibro(isbn, libro);
        
        return ResponseEntity.noContent().build();
    }


    //metodo para borrar todos los libros para pruebas, eliminar al acabar codigo
    @DeleteMapping
    public ResponseEntity<Object> borrarTodo(){
        servicio.deleteTodos();
        return ResponseEntity.noContent().build();
    }
}
