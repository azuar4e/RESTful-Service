package es.upm.sos.biblioteca.controllers;


import es.upm.sos.biblioteca.Excepciones.Libros.*;
import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.models.LibroModelAssembler;
import es.upm.sos.biblioteca.services.ServicioLibros;
import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/biblioteca.api/Libros")
@AllArgsConstructor
public class LibrosController {

    private final ServicioLibros servicio;

    private PagedResourcesAssembler<Libro> pagedResourcesAssembler;
    private LibroModelAssembler libroModelAssembler;


        @GetMapping
        public ResponseEntity<Object> getLibros(
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "3", required = false) int size) {
            //obtiene libros por paginas de tamaños dado
            Page<Libro> libros = servicio.getLibros(page, size);
            //formato para la respuesta con paginas y el assembler
            //pagedResourcesAssembler hace que se le pasen linnks de paginas
            //toModel(libros,libroModelAssembler) añade a los libros de la pagina su autoreferencia
    return ResponseEntity.ok(pagedResourcesAssembler.toModel(libros, libroModelAssembler));
        }
    
    
    

    @GetMapping(params = "tituloContiene")
    public ResponseEntity<Object>  getLibrosContenido(
                    @RequestParam String tituloContiene, 
                    @RequestParam(defaultValue = "0", required = false) int page,
                    @RequestParam(defaultValue = "3", required = false) int size) {
        //devuelve los libros que contengan en su titulo el parametro dicho
        Page<Libro> libros = servicio.getLibrosContenido(tituloContiene,page,size);

        return new ResponseEntity<>(pagedResourcesAssembler.toModel(libros, libroModelAssembler),HttpStatus.OK);

    }

    @GetMapping(params = "disponible")
    //distinto metodo para cuando haya parametro titulo_contiene
    public ResponseEntity<Object> getLibrosDisponibles(
                                @RequestParam boolean disponible,
                                @RequestParam(defaultValue = "0", required = false) int page,
                                @RequestParam(defaultValue = "3", required = false) int size) {
        try{
       //devuelve todos los libros disponibles 
            Page<Libro> libros = servicio.getLibrosDisponibles(page,size);
            return new ResponseEntity<>(pagedResourcesAssembler.toModel(libros, libroModelAssembler),HttpStatus.OK);
        }
       catch(LibroNotFoundContentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }

    @GetMapping("/{isbn}")  
    public ResponseEntity<Object> getLibroIsbn(@PathVariable String isbn){
        //devuelve un libro a partir de su isbn
        Libro libro = servicio.getLibroIsbn(isbn);
        //referencia a si mismo
        try{
            libro.add(linkTo(methodOn(LibrosController.class).getLibroIsbn(isbn)).withSelfRel());
            return ResponseEntity.ok(libro);
        }
        catch (LibroNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }

    } 

    
    
    @PostMapping
    public ResponseEntity<Object> añadirLibro(@RequestBody Libro libro){
        Libro nuevo = servicio.postLibro(libro);
    try{
            //linkea el nuevo libro a la uri creada por su isbn
            return ResponseEntity.created(linkTo(methodOn(LibrosController.class).
                                        getLibroIsbn(nuevo.getIsbn())).toUri()).build();
    } catch(LibroConflictException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
    }
    
    }



    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> eliminarLibro(@PathVariable String isbn){
        //elimina un libro por su ISBN
        try{
            servicio.deleteLibro(isbn);
            return ResponseEntity.noContent().build();
            }   
        catch (LibroNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }

    
    
    @PutMapping("/{isbn}")
    //actualiza el libro por el isbn
    public ResponseEntity<Object> modificarLibro(@PathVariable String isbn, @RequestBody Libro libro){
        try{
        servicio.actualizarLibro(isbn, libro);
        return ResponseEntity.noContent().build();
        }
        catch (LibroNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); 
        }
    }


    //metodo para borrar todos los libros para pruebas, eliminar al acabar codigo
    @DeleteMapping
    public ResponseEntity<Object> borrarTodo(){
        servicio.deleteTodos();
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{isbn}/unidades")
        public ResponseEntity<Integer> getUnidades(@RequestParam String isbn) {
            int unidades = servicio.getUnidadesLibro(isbn);
            return ResponseEntity.ok(unidades);
        }
        
}
