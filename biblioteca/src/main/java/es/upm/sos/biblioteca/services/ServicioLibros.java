package es.upm.sos.biblioteca.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import es.upm.sos.biblioteca.Excepciones.Libros.LibroConflictException;
import es.upm.sos.biblioteca.Excepciones.Libros.LibroNotFoundContentException;
import es.upm.sos.biblioteca.Excepciones.Libros.LibroNotFoundException;
import es.upm.sos.biblioteca.models.Libro;
import es.upm.sos.biblioteca.repository.LibrosRepository;
import jakarta.transaction.Transactional;
import lombok.*;

@Service //Marcamos la clase como componente de servicio
@AllArgsConstructor

public class ServicioLibros {
    @Autowired

    //repositorio al que llamamos para realizar las querys
    private final LibrosRepository repository;

    

    //metodo para sacar de la base de datos los libros en paginas
    public Page<Libro> getLibros(int page, int size){
    Pageable paginable = PageRequest.of(page, size);
        return repository.findAll(paginable);
    }

    //metodo para sacar de la base de datos libros con contenido del titulo "contenido"
    public Page<Libro> getLibrosContenido(String contenido, int page, int size){

        Pageable paginable = PageRequest.of(page, size);
        Page<Libro> libros = repository.findLibrosContenido(contenido,paginable);
        if (libros == null) {    throw new LibroNotFoundContentException(contenido);  }

        return libros;
    }

    public Page<Libro> getLibrosDisponibles(int page, int size){
        Pageable paginable = PageRequest.of(page,size);
        Page<Libro> libros = repository.findLibrosDisponibles(paginable);
        if (libros == null) {    throw new LibroNotFoundContentException("");  }

        return libros;
    }

    //metodo para sacar de la base de datos un libro a partir de su isbn
    public Libro getLibroIsbn(String isbn){
        Libro libro = repository.findByIsbn(isbn);
        if (libro == null) {    throw new LibroNotFoundException(isbn);  }

        return libro;
    }
    
    //metodo para guardar un libro en la tabla de libros/actualizar
    public Libro postLibro(Libro libro){
        Libro esta = repository.findByIsbn(libro.getIsbn());
        if(esta!=null){ throw new LibroConflictException(esta.getIsbn());   }
        
        // Libro nuevo = repository.save(libro);
        return repository.save(libro);
    }

    public Libro actualizarLibro(String isbn, Libro libroNuevo) {
        Optional<Libro> libroExistente = repository.findById(isbn);
        if (!libroExistente.isPresent()) { throw new LibroNotFoundException(isbn);  }
        else{
            Libro libro = libroExistente.get();
            libro.setTitulo(libroNuevo.getTitulo());
            libro.setAutores(libroNuevo.getAutores());
            libro.setEdicion(libroNuevo.getEdicion());
            libro.setEditorial(libroNuevo.getEditorial());

            return repository.save(libro);
        }
    }

//metodo delete
@Transactional
    public void deleteLibro(String isbn){
        Optional<Libro> libroExistente = repository.findById(isbn); 
        if(!libroExistente.isPresent()){  throw new LibroNotFoundException(isbn); }
        
        repository.deleteByIsbn(isbn);
    }

    //para pruebas borrar todos los libros, eliminar al acabar codgio
    public void deleteTodos(){
        repository.deleteAll();
    }
}
